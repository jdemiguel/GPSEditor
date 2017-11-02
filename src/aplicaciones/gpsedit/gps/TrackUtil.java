package aplicaciones.gpsedit.gps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.Seccion;
import aplicaciones.gpsedit.beans.RawSeccion;
import aplicaciones.gpsedit.beans.RawTrack;
import aplicaciones.gpsedit.beans.RawTrackPoint;
import aplicaciones.gpsedit.beans.TipoActividad;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionTrack;
import aplicaciones.gpsedit.util.UtilidadesMath;

public class TrackUtil {

	public static RawTrack toRawTrack(Track track)  {
		RawTrack rawTrack = new RawTrack();
		List<Seccion> secciones = track.getSecciones();
		for (int numSeccion = 0; numSeccion < secciones.size(); numSeccion++)  {
			Seccion seccion = secciones.get(numSeccion);
			RawSeccion rawSeccion = new RawSeccion();
			List<TrackPoint> puntos = seccion.getPuntos();
			for (int numPunto=0; numPunto< puntos.size(); numPunto++) {
				TrackPoint punto = puntos.get(numPunto);
				RawTrackPoint rawPunto = new RawTrackPoint();
				rawPunto.setAltitud(punto.getAltitud());
				rawPunto.setHr(punto.getHR());
				rawPunto.setCadencia(punto.getCadencia());
				rawPunto.setDistancia(punto.getDistanciaLeida());
				rawPunto.setLatitud(punto.getLatitud());
				rawPunto.setLongitud(punto.getLongitud());
				rawPunto.setTiempo(punto.getHora());
				rawPunto.setPotencia(punto.getPotencia());
				rawPunto.setVelocidad(punto.getVelocidadLeida());
				rawSeccion.addPunto(rawPunto);
			}			
			rawSeccion.setTriggerMethod(seccion.getTriggerMethod());
			rawSeccion.setIntensidad(seccion.getIntensidad());
			rawSeccion.setCadenciaMed(seccion.getDatos().getCadenciaMed());
			rawSeccion.setLongitud(seccion.getDatos().getLongitud());			
			rawSeccion.setHRMax(seccion.getDatos().getHrMax());
			rawSeccion.setHRMed(seccion.getDatos().getHrMed());
			rawSeccion.setTiempoTotal(seccion.getDatos().getTiempoAbsoluto());
			rawSeccion.setVelocidadMaxima(seccion.getDatos().getVelocidadMax());
			rawSeccion.setVelocidadMed(seccion.getDatos().getVelocidadMed());
			
			rawTrack.addSeccion(rawSeccion);
		}
		
		rawTrack.setAutor(track.getAutor());
		rawTrack.setDispositivo(track.getDispositivo());
		rawTrack.setNombre(track.getNombre());
		rawTrack.setTipoActividad(track.getTipoActividad());
		rawTrack.setDescripcion(track.getDescripcion());
		rawTrack.setCalorias(track.getCalorias());
		rawTrack.setTime(track.getPrimero().getHora());
		rawTrack.setHayGPS(track.isHayGPS());
		rawTrack.setHayCadencia(track.getDatos().getCadenciaMed() > 0);
		
		rawTrack.setFichero(track.getFichero());
		rawTrack.setGrabado(track.isGrabado());
		rawTrack.setTipo(track.getTipo());

		return rawTrack;
	}
    
	
	public static Track toTrack(RawTrack rawTrack) {
		ConfiguracionTrack configuracionTrack = Configuracion.getInstance().getConfiguracionTrack();
		Track track = new Track();
		List<RawSeccion> secciones = rawTrack.getSecciones();
        int numElementosPendienteSuavizada = 100; //para la pendiente vamos hacemos la media de los ultimos n valores
        
        List<Double> ultimasLongitudesPte = new ArrayList<Double>();
        List<Double> ultimasLongitudesVel = new ArrayList<Double>();
        List<Double> ultimasAltitudes = new ArrayList<Double>();
        List<Long> ultimosTiempos = new ArrayList<Long>();

        double latitudAnterior = 0.0;
		double longitudAnterior = 0.0;
		double distanciaAnterior = 0;
		double altitudAnterior = 0;
		double pendienteAnterior =0.0;
		long tiempoAbsolutoAnterior = 0;
		long tiempoMovimiento = 0;
		long distanciaVelAnterior = 0;
		
		long tiempoInicial = 0;
		int indexPunto = 0;
		for (int indexSeccion = 0; indexSeccion < secciones.size(); indexSeccion++)  {
			RawSeccion rawSeccion = secciones.get(indexSeccion);
			Seccion seccion = new Seccion();
			seccion.setTexto(String.valueOf(indexSeccion + 1));
			seccion.setInicioRango(indexPunto);
			List<RawTrackPoint> rawPuntos = rawSeccion.getPuntos();
			for (int i=0; i< rawPuntos.size(); i++) {
				RawTrackPoint rawPunto = rawPuntos.get(i);
				TrackPoint punto = new TrackPoint();
				try  {

					//guardamos los datos crudos en la tabla
					double latitud = rawPunto.getLatitud();
					double longitud = rawPunto.getLongitud();
					double distancia = rawPunto.getDistancia();
					double altitud = rawPunto.getAltitud();
					double velocidadLeida = rawPunto.getVelocidad();
					long hr = rawPunto.getHr();
					long cadencia = rawPunto.getCadencia();
					long potencia = rawPunto.getPotencia();
					Date hora = rawPunto.getTiempo();
					if (i==0 && indexSeccion == 0) tiempoInicial = hora.getTime();
					long tiempoAbsoluto = hora.getTime() - tiempoInicial;
					long distanciaVel = Math.round(rawPunto.getVelocidad()*(tiempoAbsoluto - tiempoAbsolutoAnterior) / 1000.0) + distanciaVelAnterior;
					
					//se ha perdido la señal GPS. si hay datos anteriores de altitud los aprovechamos. 
					//if (altitud == 0) altitud = altitudAnterior;
					
		        	punto.setAltitud(altitud);
		        	punto.setCadencia(cadencia);
		        	punto.setPotencia(potencia);
		        	punto.setHR(hr);
		        	punto.setLatitud(latitud);
		        	punto.setLongitud(longitud);
		        	punto.setTiempoAbsoluto(tiempoAbsoluto);
		        	punto.setHora(hora);
		        	punto.setVelocidadLeida(velocidadLeida);
					
		        	punto.setDistanciaLeida(distancia);
		        	
					double pendiente = 0.0;
					double velocidad = 0.0;
					
		       		//si en este punto no hay distancia en el fichero GPS la calculamos desde la anterior (que puede ser real o calculada) más el incremento des calculo de distancia por valores latitud y longitud
		       		if ((i > 0 || indexSeccion > 0) && distancia == 0.0) {
		       			//descartamos el punto si no tiene info GPS ni velocidad del sensor
		       			if (latitud == 0 || longitud == 0 || latitudAnterior == 0 || longitudAnterior == 0) continue;
	       				distancia = distanciaAnterior + UtilidadesMath.calculoDistancia(latitud, longitud, latitudAnterior, longitudAnterior);
	    				punto.setDistanciaCalculada(distancia);
		       		} else {
			       		// podría ser que la distancia que llega fuera menor que la anterior si el GPS puso un punto raro anterior sin distancia medida. Descartamos ese punto
			       		try  {
				       		while (distancia > 0 && (distancia < distanciaAnterior)) {
				       			track.borraUltimo();
				       			seccion.borraUltimo();
				       			distanciaAnterior = track.getUltimo().getDistancia();
				       		}
			       		} catch (Exception e)  {
			       			GPSEdit.logger.error("Algo hay mal en este fichero con las distancias ", e);
			       		}
		       			
		       		}
		        	punto.setDistancia(distancia);

		       		//calculo de velocidad, pendiente y valores medios
		       		if ((i > 0 || indexSeccion > 0))  {
		       			double incDistancia = distancia - distanciaAnterior;
		       			double desnivel = altitud - altitudAnterior;
		       			long incTiempo = tiempoAbsoluto - tiempoAbsolutoAnterior;
		       			
		       			if (incTiempo <0 )  {
		       				System.out.println("3");
		       			}
		       			
		       			//desechamos el cambio de desnivel si no había dato de altitud en el punto anterior
		       			if (altitudAnterior == 0) desnivel = 0;
		       			
		       			//si no hay avance en distancia o tiempo lo eliminamos
		       			if (incDistancia == 0 || incTiempo == 0) continue;
	       				//si se ha perdido unos cuantos puntos y la velocidad media con el anterior es menor de 3,6 km/h ... cero velocidad al anterior y cero a este
		       			//en cambio en pendiente eliminamos este punto para que no se tenga en cuenta por si es disparatado
	       				if (incTiempo > configuracionTrack.getTiempoMinimo() && incDistancia < configuracionTrack.getDistanciaMinima())  {
	       					ultimasLongitudesVel.clear();
	       					ultimosTiempos.clear();
	       					ultimasLongitudesPte.clear();
	       					ultimasAltitudes.clear();
	       					TrackPoint ultimo = track.getUltimo();
	       					if (track.getPuntos().size() > 0)
	       					if (ultimo != null)  {
	       						GPSEdit.logger.debug("Cambiando el punto " + i + ". incDistancia:" + incDistancia + ". incTiempo:" + incTiempo);
	    		        		ultimo.setVelocidad(0.0);
	    		        	}
	       					velocidad = 0.0;
	       					pendiente = pendienteAnterior;
	       				} else {
	       					ultimasLongitudesVel.add(incDistancia);
	       					ultimosTiempos.add(incTiempo);
	       					ultimasLongitudesPte.add(incDistancia);
	       					ultimasAltitudes.add(desnivel);
	       				}

		       			if (ultimasLongitudesVel.size() > numElementosPendienteSuavizada) ultimasLongitudesVel.remove(0);
		       			if (ultimasLongitudesPte.size() > numElementosPendienteSuavizada) ultimasLongitudesPte.remove(0);
		       			if (ultimosTiempos.size() > numElementosPendienteSuavizada) ultimosTiempos.remove(0);
		       			if (ultimasAltitudes.size() > numElementosPendienteSuavizada) ultimasAltitudes.remove(0);
		       			
	       				double longitudAcumulada = 0;
	       				long tiempoAcumulado = 0;
	       				
	       				if (ultimasLongitudesVel.size() > 0)  {
		       				for (int index=ultimasLongitudesVel.size()-1; index >= 0; index--)  {
		       					if ((index < ultimasLongitudesVel.size()-1) && ultimasLongitudesVel.get(index) == 0) break;
		       					longitudAcumulada += ultimasLongitudesVel.get(index);
		       					tiempoAcumulado += ultimosTiempos.get(index);
		       				}
			       			velocidad = (double)Math.round((3600.0 * longitudAcumulada * 1000.0 / (double)tiempoAcumulado)) / 1000.0;
			       			longitudAcumulada = 0;
	       				}
	       				double desnivelAcumulada = 0;
	       				if (ultimasLongitudesPte.size() > 0)  {
		       				for (int index=ultimasLongitudesPte.size()-1; index >= 0; index--)  {
		       					if ((index < ultimasLongitudesPte.size()-1) && ultimasLongitudesPte.get(index) == 0) break;
		       					longitudAcumulada += ultimasLongitudesPte.get(index);
		       					desnivelAcumulada += ultimasAltitudes.get(index);
		       				}
		       				pendiente = Math.round((desnivelAcumulada * 1000.0 / longitudAcumulada)) / 1000.0;
	       				}
		       		    
	       				//valor disparatado por incrementarse mucho
	       				if (Math.abs(pendienteAnterior - pendiente) > configuracionTrack.getCambioMaximoPendiente()) {
//	       					System.out.println("Valor disparatado de pendiente. Punto:" + puntos.size() + ". Pendiente:" + pendiente + ". Pendiente anterior:" + pendienteAnterior);
	       					pendiente = pendienteAnterior; 
	       					if (ultimasLongitudesPte.size() > 0) ultimasLongitudesPte.remove(ultimasLongitudesPte.size()-1);
	       					if (ultimasAltitudes.size() > 0) ultimasAltitudes.remove(ultimasAltitudes.size()-1);
	       				}
			        	
			        	if (velocidad > configuracionTrack.getVelocidadMinima()) tiempoMovimiento = tiempoMovimiento + incTiempo;
			        	
			        	punto.setPendiente(pendiente);
			        	punto.setDesnivel(desnivel);
			        }

		        	punto.setTiempoMovimiento(tiempoMovimiento);
		        	if (velocidadLeida != 0) punto.setVelocidad(velocidadLeida); 
		        	else punto.setVelocidad(velocidad);
		        	punto.setVelocidadCalculada(velocidad);
		        	punto.setPaso(3600000.0 / punto.getVelocidad());

					distanciaAnterior = distancia;
					latitudAnterior = latitud;
					longitudAnterior = longitud;
					tiempoAbsolutoAnterior = tiempoAbsoluto;
					altitudAnterior = altitud;
					pendienteAnterior = pendiente;
					distanciaVelAnterior = distanciaVel;

				} catch (Exception e) {
					GPSEdit.logger.error("Error en punto " + i, e);
				}
				seccion.addPunto(punto);
				track.addPunto(punto);
				indexPunto++;
			}			
			if (seccion.getPuntos().size() > 1) {
				seccion.setFinRango(indexPunto - 1);
				seccion.setDatos(TrackUtil.getDatos(seccion.getPuntos()));
				track.addSeccion(seccion);
			}
			seccion.setTriggerMethod(rawSeccion.getTriggerMethod());
			if (seccion.getTriggerMethod() ==  null) seccion.setTriggerMethod("Manual");
			seccion.setIntensidad(rawSeccion.getIntensidad());
			if (seccion.getIntensidad() ==  null) seccion.setIntensidad("Active");
		}
		track.setDatos(TrackUtil.getDatos(track.getPuntos()));
		
		track.setAutor(rawTrack.getAutor());
		track.setDispositivo(rawTrack.getDispositivo());
		track.setNombre(rawTrack.getNombre());
		track.setDescripcion(rawTrack.getDescripcion());
		track.setCalorias(rawTrack.getCalorias());
		track.setHayGPS(rawTrack.isHayGPS());
		
		track.setFichero(rawTrack.getFichero());
		track.setGrabado(rawTrack.isGrabado());
		track.setTipo(rawTrack.getTipo());

		track.setTipoActividad(rawTrack.getTipoActividad());
		if (rawTrack.getTipoActividad() == null) {
			TipoActividad actividades[] = {
					TipoActividad.RUNNING, TipoActividad.CICLISMO_CARRETERA,
					TipoActividad.CICLISMO_MTB, TipoActividad.CICLISMO_INTERIOR, 
					TipoActividad.SENDERISMO, TipoActividad.ESQUI, 
					TipoActividad.NATACION
					};
			TipoActividad tipo =  (TipoActividad) JOptionPane.showInputDialog(null,
					"Selecciona el tipo de Actividad",
	                "Tipo de Actividad",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                actividades,
	                TipoActividad.RUNNING);
			if (tipo == null) tipo = TipoActividad.OTROS;
			track.setTipoActividad(tipo);
		}
		
		return track;
	}
	
	public static DatosSegmentoBean getDatos(List<TrackPoint> puntos) {
		DatosSegmentoBean datosSegmento = new DatosSegmentoBean();
		if (puntos.size() < 2) return datosSegmento;
		
		TrackPoint puntoInicio = puntos.get(0);
		TrackPoint puntoFin = puntos.get(puntos.size() - 1);
		
		long tiempoMovimiento = 0 ;
		long tiempoAbsoluto = 0;
		long cadenciaMed = 0;
		long cadenciaMax = 0;
		long cadenciaMin = 10000;
		long potenciaMed = 0;
		long potenciaMax = 0;
		long potenciaMin = 10000;
		long numElementosCadenciaMed = 0;
		long numElementosHRMed = 0;
		long numElementosPotenciaMed = 0;
		long numElementosAltitudMed = 0;
		long numElementosVelocidadMed = 0;
		long hrMed = 0;
		long hrMax = 0;
		long hrMin = 10000;
		double velocidadMed = 0;
		double velocidadMax = 0;
		double velocidadMin = 10000;
		double pendienteMax = 0;
		double pendienteMin = 10000;
		double altitudMed = 0;
		double altitudMax = 0;
		double altitudMin = 10000;
		double desnivelAcumulado = 0;
		double longitud;

		long distanciaInicial = Math.round(Math.floor(puntos.get(0).getDistancia()));
		long distanciaFinal = Math.round(Math.floor(puntoFin.getDistancia()));

		long tiempoInicial = puntoInicio.getTiempoMovimiento();
		long tiempoFinal = puntoFin.getTiempoMovimiento();

    	Date horaInicio = puntoInicio.getHora();
    	Date horaFin = puntoFin.getHora();
    	
    	longitud = puntoFin.getDistancia() - puntoInicio.getDistancia();
    	tiempoAbsoluto = puntoFin.getTiempoAbsoluto() - puntoInicio.getTiempoAbsoluto();
    	tiempoMovimiento = puntoFin.getTiempoMovimiento() - puntoInicio.getTiempoMovimiento();
    	
    	for (int i = 0; i < puntos.size(); i++)  {
    		TrackPoint punto = puntos.get(i);
    		
        	double altitud = punto.getAltitud();
        	if (altitud > altitudMax) altitudMax = altitud;
        	if (altitud < altitudMin && altitud > 0) altitudMin = altitud;

        	long hr = punto.getHR();
        	if (hr > hrMax) hrMax = hr;
        	if (hr < hrMin && hr > 0) hrMin = hr;

        	long cadencia = punto.getCadencia();
        	if (cadencia > cadenciaMax) cadenciaMax = cadencia;
        	if (cadencia < cadenciaMin) cadenciaMin = cadencia;

        	long potencia = punto.getPotencia();
        	if (potencia > potenciaMax) potenciaMax = potencia;
        	if (potencia < potenciaMin) potenciaMin = potencia;
        	
        	potenciaMed += potencia;
    		numElementosPotenciaMed++;
        	
        	if (cadencia > 0) {
        		cadenciaMed += cadencia;
        		numElementosCadenciaMed++;;
        	}
   			if (hr > 0) {
	        	hrMed += hr;
        		numElementosHRMed++;;
        	}
   			if (altitud > 0) {
	        	altitudMed += altitud;
        		numElementosAltitudMed++;;
        	}
   			numElementosVelocidadMed++;

    		double pendiente = punto.getPendiente();
        	if (pendiente > pendienteMax) pendienteMax = pendiente;
        	if (pendiente < pendienteMin) pendienteMin = pendiente;

        	double velocidad = punto.getVelocidad();
        	if (velocidad > velocidadMax) velocidadMax = velocidad;
        	if (velocidad < velocidadMin) velocidadMin = velocidad;
        	velocidadMed += velocidad;

        	double desnivel = punto.getDesnivel();       	
        	if (desnivel > 0) desnivelAcumulado += desnivel;
        	
    	}

		double primeraAltitud = 0;
		double ultimaAltitud = 0;
		for (int index  = 0; index < puntos.size(); index++)  {
			if (primeraAltitud == 0) primeraAltitud = puntos.get(index).getAltitud();
			if (puntos.get(index).getAltitud() != 0) ultimaAltitud = puntos.get(index).getAltitud();
		}
		
		double desnivelTotal = ultimaAltitud - primeraAltitud;
		double pendienteMed = desnivelTotal / (double)longitud;

		if (numElementosCadenciaMed > 0) cadenciaMed = Math.round(cadenciaMed / (double)numElementosCadenciaMed);
        else cadenciaMed = 0;
		if (numElementosPotenciaMed > 0) potenciaMed = Math.round(potenciaMed / (double)numElementosPotenciaMed);
        else potenciaMed = 0;
		if (numElementosHRMed > 0) hrMed = Math.round(hrMed / (double)numElementosHRMed);
        else hrMed = 0;
		if (numElementosAltitudMed > 0) altitudMed = Math.round(altitudMed / (double)numElementosAltitudMed);
        else altitudMed = 0;
		if (numElementosVelocidadMed > 0) velocidadMed = velocidadMed / (double)numElementosVelocidadMed;
        else velocidadMed = 0;

		if (altitudMin == 10000) altitudMin = 0;
		if (hrMin == 10000) hrMin = 0;
		if (cadenciaMin == 10000) cadenciaMin = 0;
		if (velocidadMin == 10000) velocidadMin = 0;
		if (pendienteMin == 10000) pendienteMin = 0;
		if (potenciaMin == 10000) potenciaMin = 0;
		
		datosSegmento.setDistanciaInicial(distanciaInicial);
		datosSegmento.setDistanciaFinal(distanciaFinal);
		datosSegmento.setTiempoInicial(tiempoInicial);
		datosSegmento.setTiempoFinal(tiempoFinal);
		datosSegmento.setHoraInicio(horaInicio);
		datosSegmento.setHoraFin(horaFin);
		datosSegmento.setTiempoMovimiento(tiempoMovimiento);
		datosSegmento.setTiempoAbsoluto(tiempoAbsoluto);
		datosSegmento.setLongitud(longitud);
		datosSegmento.setDesnivelTotal(desnivelTotal);
		datosSegmento.setDesnivelAcumulado(desnivelAcumulado);
		datosSegmento.setAltitudMax(altitudMax);
		datosSegmento.setAltitudMed(altitudMed);
		datosSegmento.setAltitudMin(altitudMin);
		datosSegmento.setHrMed(hrMed);
		datosSegmento.setHrMin(hrMin);
		datosSegmento.setHrMax(hrMax);
		datosSegmento.setCadenciaMed(cadenciaMed);
		datosSegmento.setCadenciaMin(cadenciaMin);
		datosSegmento.setCadenciaMax(cadenciaMax);
		datosSegmento.setVelocidadMed(velocidadMed);
		datosSegmento.setVelocidadMin(velocidadMin);
		datosSegmento.setVelocidadMax(velocidadMax);
		datosSegmento.setPasoMed(3600000.0 / velocidadMed);
		datosSegmento.setPasoMin(3600000.0 / velocidadMin);
		datosSegmento.setPasoMax(3600000.0 / velocidadMax);
		datosSegmento.setPotenciaMed(potenciaMed);
		datosSegmento.setPotenciaMin(potenciaMin);
		datosSegmento.setPotenciaMax(potenciaMax);
		
		datosSegmento.setPendienteMed(pendienteMed);
		datosSegmento.setPendienteMin(pendienteMin);
		datosSegmento.setPendienteMax(pendienteMax);
		
		return datosSegmento;
	}
	
	
}

package aplicaciones.gpsedit.gps;

import java.util.Date;
import java.util.List;

import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.beans.RawSeccion;
import aplicaciones.gpsedit.beans.RawTrack;
import aplicaciones.gpsedit.beans.RawTrackPoint;
import aplicaciones.gpsedit.beans.Seccion;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionTrack;
import aplicaciones.gpsedit.util.UtilidadesMath;

public class TrackUtil {

	public static RawTrack toRawTrack(Track track)  {
		RawTrack rawTrack = new RawTrack();
		List<Seccion> secciones = track.getSecciones();
		boolean hayCadencia = false;
		boolean hayGPS = false;
		boolean hayHR = false;
		boolean hayAltitud = false;
		boolean hayPotencia = false;
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
				if (punto.getCadencia() > 0)  hayCadencia = true;
				if (punto.getLatitud() > 0)  hayGPS = true;
				if (punto.getHR() > 0)  hayHR = true;
				if (punto.getAltitud() > 0)  hayAltitud = true;
				if (punto.getPotencia() > 0)  hayPotencia = true;
			}			
			rawSeccion.setTriggerMethod(seccion.getTriggerMethod());
			rawSeccion.setIntensidad(seccion.getIntensidad());
			//rawSeccion.setCadenciaMed(seccion.getDatos().getCadenciaMed());
			//rawSeccion.setLongitud(seccion.getDatos().getLongitud());			
			//rawSeccion.setHRMax(seccion.getDatos().getHrMax());
			//rawSeccion.setHRMed(seccion.getDatos().getHrMed());
			//rawSeccion.setTiempoTotal(seccion.getDatos().getTiempoAbsoluto());
			//rawSeccion.setVelocidadMaxima(seccion.getDatos().getVelocidadMax());
			//rawSeccion.setVelocidadMed(seccion.getDatos().getVelocidadMed());
			
			rawTrack.addSeccion(rawSeccion);
		}
		
		rawTrack.setAutor(track.getAutor());
		rawTrack.setDispositivo(track.getDispositivo());
		rawTrack.setNombre(track.getNombre());
		rawTrack.setTipoActividad(track.getTipoActividad());
		rawTrack.setDescripcion(track.getDescripcion());
		rawTrack.setCalorias(track.getCalorias());
		rawTrack.setTime(track.getPrimero().getHora());
		rawTrack.setGPS(hayGPS);
		rawTrack.setAltitud(hayAltitud);
		rawTrack.setCadencia(hayCadencia);
		rawTrack.setHr(hayHR);
		rawTrack.setPotencia(hayPotencia);
		
		rawTrack.setFichero(track.getFichero());
		rawTrack.setGrabado(track.isGrabado());
		rawTrack.setTipo(track.getTipo());

		return rawTrack;
	}
    
	
	public static Track toTrack(RawTrack rawTrack) {
		ConfiguracionTrack configuracionTrack = Configuracion.getInstance().getConfiguracionTrack();
		Track track = new Track();
		List<RawSeccion> secciones = rawTrack.getSecciones();

        double latitudAnterior = 0.0;
		double longitudAnterior = 0.0;
		double distanciaAnterior = 0;
		double altitudAnterior = 0;
		double pendienteBrutaAnterior =0.0;
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
		        	
					double pendienteBruta = 0.0;
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
	       					TrackPoint ultimo = track.getUltimo();
	       					if (track.getPuntos().size() > 0)
	       					if (ultimo != null)  {
	       						GPSEdit.logger.debug("Cambiando el punto " + i + ". incDistancia:" + incDistancia + ". incTiempo:" + incTiempo);
	    		        		ultimo.setVelocidad(0.0);
	    		        	}
	       					velocidad = 0.0;
	       					pendienteBruta = pendienteBrutaAnterior;
	       				}

		       			velocidad = (double)Math.round((3600.0 * incDistancia * 1000.0 / (double)incTiempo)) / 1000.0;
		       			pendienteBruta = Math.round((desnivel * 1000.0 / incDistancia)) / 1000.0;

	       				
			        	if (velocidad > configuracionTrack.getVelocidadMinima()) tiempoMovimiento = tiempoMovimiento + incTiempo;
			        	
			        	punto.setPendienteBruta(pendienteBruta);
			        	punto.setDesnivel(desnivel);
			        }

		        	punto.setTiempoMovimiento(tiempoMovimiento);
		        	if (velocidadLeida != 0) punto.setVelocidad(velocidadLeida); 
		        	else punto.setVelocidad(velocidad);
		        	punto.setVelocidadCalculada(velocidad);
		        	if (punto.getVelocidad() > 0) punto.setPaso(3600000.0 / punto.getVelocidad());
		        	else punto.setPaso(0.0);

					distanciaAnterior = distancia;
					latitudAnterior = latitud;
					longitudAnterior = longitud;
					tiempoAbsolutoAnterior = tiempoAbsoluto;
					altitudAnterior = altitud;
					pendienteBrutaAnterior = pendienteBruta;
					distanciaVelAnterior = distanciaVel;

				} catch (Exception e) {
					GPSEdit.logger.error("Error en punto " + i, e);
				}
				seccion.addPunto(punto);
				track.addPunto(punto);
				indexPunto++;
			}			
			if (seccion.getPuntos().size() > 1) {
				seccion.setFinRango(indexPunto - 2);
				track.addSeccion(seccion);
			}
			seccion.setTriggerMethod(rawSeccion.getTriggerMethod());
			if (seccion.getTriggerMethod() ==  null) seccion.setTriggerMethod("Manual");
			seccion.setIntensidad(rawSeccion.getIntensidad());
			if (seccion.getIntensidad() ==  null) seccion.setIntensidad("Active");
		}
		track.setAutor(rawTrack.getAutor());
		track.setDispositivo(rawTrack.getDispositivo());
		track.setNombre(rawTrack.getNombre());
		track.setDescripcion(rawTrack.getDescripcion());
		track.setCalorias(rawTrack.getCalorias());
		track.setGPS(rawTrack.isGPS());
		track.setCadencia(rawTrack.isCadencia());
		track.setAltitud(rawTrack.isAltitud());
		track.setHr(rawTrack.isHr());
		track.setPotencia(rawTrack.isPotencia());
		
		track.setFichero(rawTrack.getFichero());
		track.setGrabado(rawTrack.isGrabado());
		track.setTipo(rawTrack.getTipo());

		track.setTipoActividad(rawTrack.getTipoActividad());
		
		return track;
	}
	
}

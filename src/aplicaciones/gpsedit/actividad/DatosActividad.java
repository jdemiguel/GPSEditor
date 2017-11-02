package aplicaciones.gpsedit.actividad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.beans.ActividadBean;
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.DatosTodasGraficasBean;
import aplicaciones.gpsedit.beans.Seccion;
import aplicaciones.gpsedit.beans.SegmentoCoeficiente;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionSecciones;
import aplicaciones.gpsedit.util.EjeX;
import aplicaciones.gpsedit.util.UtilidadesFormat;
import aplicaciones.gpsedit.util.UtilidadesMath;


public class DatosActividad {
	
	private ActividadBean actividadBean;
	DatosTodasGraficasBean datosGraficasDistancia = null;
	DatosTodasGraficasBean datosGraficasTiempoMov = null;
	DatosTodasGraficasBean datosGraficasTiempoAbs = null;
	DatosTodasGraficasBean datosGraficasHora = null;
	List<Seccion> seccionesAutomaticas = null;
	DatosSegmentoBean datosTrack;
	
	public DatosActividad(Track track) {
		this.actividadBean = new ActividadBean();
		this.actividadBean.setTrack(track);
		this.actividadBean.setInicioRango(0);
		this.actividadBean.setFinRango(track.getPuntos().size() - 1);
		this.actividadBean.setPuntoSeleccionado(0);
		this.datosTrack = getDatos(0, track.getPuntos().size()-1);

	}

	
	public DatosSegmentoBean getDatos(int inicio, int fin)  {
		
		List<TrackPoint> puntos = getTrack().getPuntos();
		DatosSegmentoBean datosSegmento = new DatosSegmentoBean();
		if (puntos.size() < 2) return datosSegmento;
		
		TrackPoint puntoInicio = puntos.get(inicio);
		TrackPoint puntoFin = puntos.get(fin);
		
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

		long distanciaInicial = Math.round(Math.floor(puntoInicio.getDistancia()));
		long distanciaFinal = Math.round(Math.floor(puntoFin.getDistancia()));

		long tiempoInicial = puntoInicio.getTiempoMovimiento();
		long tiempoFinal = puntoFin.getTiempoMovimiento();

    	Date horaInicio = puntoInicio.getHora();
    	Date horaFin = puntoFin.getHora();
    	
    	longitud = puntoFin.getDistancia() - puntoInicio.getDistancia();
    	tiempoAbsoluto = puntoFin.getTiempoAbsoluto() - puntoInicio.getTiempoAbsoluto();
    	tiempoMovimiento = puntoFin.getTiempoMovimiento() - puntoInicio.getTiempoMovimiento();
    	
    	for (int i = inicio; i <= fin; i++)  {
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

    		double pendiente = getPendiente(i);
        	if (pendiente > pendienteMax) pendienteMax = pendiente;
        	if (pendiente < pendienteMin) pendienteMin = pendiente;

        	double velocidad = getVelocidad(i);
        	if (velocidad > velocidadMax) velocidadMax = velocidad;
        	if (velocidad < velocidadMin) velocidadMin = velocidad;
        	velocidadMed += velocidad;

        	double desnivel = punto.getDesnivel();       	
        	if (desnivel > 0) desnivelAcumulado += desnivel;
        	
    	}

		double primeraAltitud = 0;
		double ultimaAltitud = 0;
		for (int index  = inicio; index <= fin; index++)  {
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
	
	public List<Seccion> getSeccionesAutomaticas() {
		if (seccionesAutomaticas == null)  {
			ConfiguracionSecciones configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones();
			seccionesAutomaticas = new ArrayList<Seccion>();
			List<TrackPoint> puntos = getTrack().getPuntos();
			double intervalo = configuracion.getIntervaloSeccionAutomatica();
			EjeX ejeX = configuracion.getEjeXSeccionAutomatica();

			double total = getValorX(getTrack().getUltimo(), ejeX);
			double valorInicio = 0;
			if (ejeX.isHora()) valorInicio  = getValorX(getTrack().getPrimero(), ejeX);
			int numSecciones = (int) Math.ceil((total  - valorInicio) / intervalo);
	
			for (int numSeccion = 0; numSeccion < numSecciones; numSeccion++) {
				Seccion seccion = new Seccion();
				StringBuffer texto = new StringBuffer();
				seccionesAutomaticas.add(seccion);
				if (configuracion.getEjeXSeccionAutomatica().isDistancia()) {
					if  (configuracion.getIntervaloSeccionAutomatica() == 1)  {
						texto.append(String.valueOf(numSeccion + 1));
					} else {
						texto.append(String.valueOf(numSeccion * configuracion.getIntervaloSeccionAutomatica()));
						texto.append("-");
						texto.append(String.valueOf((numSeccion + 1) * configuracion.getIntervaloSeccionAutomatica()));
					}
				} else {
					String valor0 = UtilidadesFormat.getHoraFormat().format(valorInicio + (numSeccion * configuracion.getIntervaloSeccionAutomatica()));
					String valor1 = UtilidadesFormat.getHoraFormat().format(valorInicio + ((numSeccion + 1) * configuracion.getIntervaloSeccionAutomatica()));
					if  (configuracion.getIntervaloSeccionAutomatica() == 60000)  {
						texto.append(valor1);
					} else {
						texto.append(valor0);
						texto.append("-");
						texto.append(valor1);
					}
				}
				seccion.setTexto(texto.toString());
			}
			
			for (int index=0; index< puntos.size(); index++ )  {
				TrackPoint punto = puntos.get(index);
				int numSeccion = ((int) Math.ceil((getValorX(punto, ejeX) - valorInicio) / intervalo)) - 1;
				if (numSeccion < 0) numSeccion = 0;
				Seccion seccion = seccionesAutomaticas.get(numSeccion);
				if (seccion.getInicioRango() == 0) seccion.setInicioRango(index);
				seccion.setFinRango(index);
				seccion.getPuntos().add(punto);
			}
		}		
		return seccionesAutomaticas;
	}

	public Track getTrack() {
		return actividadBean.getTrack();
	}	
	public int getInicioRango() {
		return actividadBean.getInicioRango();
	}
	public int getFinRango() {
		return actividadBean.getFinRango();
	}
	
	public TrackPoint getPuntoInicioRango() {
		return getTrack().getPuntos().get(getInicioRango());
	}

	public TrackPoint getPuntoFinRango() {
		return getTrack().getPuntos().get(getFinRango());
	}
	
	public int getPuntoSeleccionado() {
		return actividadBean.getPuntoSeleccionado();
	}
	
	public void resetRango() {
		actividadBean.setInicioRango(0);
		actividadBean.setFinRango(getTrack().getPuntos().size() - 1);
	}

	public void setRango(int inicio, int fin) {
		setInicioRango(inicio);
		setFinRango(fin);
	}		
	
	public void setInicioRango(int inicio) {
		GPSEdit.logger.debug("setInicioRango:" + inicio);
    	List<TrackPoint> puntos = getTrack().getPuntos();
		if (inicio >= (puntos.size()-1)) inicio = puntos.size() - 2;
		if (inicio < 0) inicio = 0;
		actividadBean.setInicioRango(inicio);
	}

	public void setFinRango(int fin) {
		GPSEdit.logger.debug("setFinRango:" + fin);
		if (fin <= actividadBean.getInicioRango()) fin = actividadBean.getInicioRango() + 1;
    	List<TrackPoint> puntos = getTrack().getPuntos();
		if (fin < 0) fin = 0;
		if (fin >= puntos.size()) fin = puntos.size() - 1;
		actividadBean.setFinRango(fin);
	}
	public void setPuntoSeleccionado(int punto) {
		if (punto >= actividadBean.getFinRango()) punto = actividadBean.getFinRango();
		if (punto < actividadBean.getInicioRango()) punto = actividadBean.getInicioRango();
		actividadBean.setPuntoSeleccionado(punto);
	}

	public double getValorX(TrackPoint punto, EjeX ejeX)  {
    	if (ejeX.isDistancia()) return ((double)punto.getDistancia() / 1000.0);
    	else if (ejeX.isTiempoAbsoluto()) return (double)punto.getTiempoAbsoluto();
    	else if (ejeX.isTiempoMovimiento()) return (double)punto.getTiempoMovimiento();
    	//La hora la cogemos restando el día de hoy a las 00
    	Calendar dia = Calendar.getInstance();
    	dia.setTime(punto.getHora());
    	dia.set(Calendar.HOUR_OF_DAY, 0);
    	dia.set(Calendar.MINUTE, 0);
    	dia.set(Calendar.SECOND, 0);
    	dia.set(Calendar.MILLISECOND, 0);
    	return (double)(punto.getHora().getTime() - dia.getTimeInMillis());
	}
	
	public void setRangoByEjeX(double ejeXInicial, double ejeXFinal, EjeX ejeX) {
		GPSEdit.logger.debug("Rango por x:" + ejeXInicial + "," + ejeXFinal);
    	int inicio = getPuntoByEjeX(ejeXInicial, ejeX);
    	int fin = getPuntoByEjeX(ejeXFinal, ejeX);
    	if (fin < (getTrack().getPuntos().size()-1)) fin++;
    	setRango(inicio, fin);
	}		
	
	public int getPuntoByEjeX(double valorEjeX, EjeX ejeX)  {
    	List<TrackPoint> puntos = getTrack().getPuntos();
    	int index = 0;
    	while (index < puntos.size() && getValorX(puntos.get(index), ejeX) <= valorEjeX) index++;
    	if (index > 0) index--;
    	return index;
	}		
	
	public DatosTodasGraficasBean getDatosGrafica(EjeX ejeX)  {
		if (ejeX.isDistancia()) return getDatosGraficasDistancia(ejeX);
		else if (ejeX.isTiempoAbsoluto()) return getDatosGraficasTiempoAbs(ejeX);
		else if (ejeX.isTiempoMovimiento()) return getDatosGraficasTiempoMov(ejeX);
		else return getDatosGraficasHora(ejeX);
	}
	
	private DatosTodasGraficasBean getDatosGraficasDistancia(EjeX ejeX) {
		if (datosGraficasDistancia == null) setDatosGrafica(ejeX);
		return datosGraficasDistancia;
	}

	private DatosTodasGraficasBean getDatosGraficasTiempoMov(EjeX ejeX) {
		if (datosGraficasTiempoMov == null) setDatosGrafica(ejeX);
		return datosGraficasTiempoMov;
	}

	private DatosTodasGraficasBean getDatosGraficasTiempoAbs(EjeX ejeX) {
		if (datosGraficasTiempoAbs == null) setDatosGrafica(ejeX);
		return datosGraficasTiempoAbs;
	}

	private DatosTodasGraficasBean getDatosGraficasHora(EjeX ejeX) {
		if (datosGraficasHora == null) setDatosGrafica(ejeX);
		return datosGraficasHora;
	}
	
	public double getPaso(int punto)  {
		TrackPoint trackPoint = getTrack().getPuntos().get(punto);
		if (trackPoint.getVelocidadLeida() > 0) return trackPoint.getPaso();
		EjeX ejeX = EjeX.getInstanciaEjeDistancia();
		UnivariateFunction pasosInterpolados = getDatosGrafica(ejeX).getDatosPaso().getDatosSuavizados(9);
		return pasosInterpolados.value(getValorX(trackPoint, ejeX)) * 60000;
	}	
	
	public double getVelocidad(int punto)  {
		TrackPoint trackPoint = getTrack().getPuntos().get(punto);
		if (trackPoint.getVelocidadLeida() > 0) return trackPoint.getVelocidadLeida();
		EjeX ejeX = EjeX.getInstanciaEjeDistancia();
		UnivariateFunction velocidadesInterpoladas = getDatosGrafica(ejeX).getDatosVelocidad().getDatosSuavizados(9);
		return velocidadesInterpoladas.value(getValorX(trackPoint, ejeX));
	}
	
	public double getPendiente(int punto)  {
		EjeX ejeX = EjeX.getInstanciaEjeDistancia();
		UnivariateFunction pendientesInterpoladas = getDatosGrafica(ejeX).getDatosPendiente().getDatosSuavizados(9);
		return pendientesInterpoladas.value(getValorX(getTrack().getPuntos().get(punto), ejeX)) / 100.0;
	}

	public List<SegmentoCoeficiente> getSegmentosCoeficienteAPMRango()  {
		return getSegmentosCoeficienteAPM(getPuntoInicioRango().getDistancia(), getPuntoFinRango().getDistancia());
	}
	
	private double getCoeficienteAPMRango()  {
		return getCoeficienteAPM(getPuntoInicioRango().getDistancia(), getPuntoFinRango().getDistancia());
	}

	public double getCoeficienteAPMTotal()  {
		return getCoeficienteAPM(getTrack().getPrimero().getDistancia(), getTrack().getUltimo().getDistancia());
	}

	
	private double getCoeficienteAPM(double posicionInicial, double posicionFinal)  {
		List<SegmentoCoeficiente> segmentos = getSegmentosCoeficienteAPM(posicionInicial, posicionFinal);
		double coeficienteAcumulado = 0;
		for (SegmentoCoeficiente segmento:segmentos) coeficienteAcumulado += segmento.getCoeficiente();
        return coeficienteAcumulado;
	}
	
	private List<SegmentoCoeficiente> getSegmentosCoeficienteAPM(double posicionInicial, double posicionFinal)  {
		List<SegmentoCoeficiente> segmentos = new ArrayList<SegmentoCoeficiente>();
		try  {
			int intervalo = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionCoeficientes().getIntervalo();
			UnivariateFunction altitudesInterpoladas = getDatosGrafica(EjeX.getInstanciaEjeDistancia()).getDatosAltitud().getDatosBrutos();
	        double posicion = posicionInicial;
	        while (posicion < posicionFinal) {
	        	SegmentoCoeficiente segmento = new SegmentoCoeficiente();
	        	double longitud = (double)intervalo;        	
	        	if ((posicion + intervalo) > posicionFinal)  {
	        		longitud = posicionFinal - posicion;
	        	}
	        	double alturaFinal = altitudesInterpoladas.value((posicion + longitud ) / 1000.0);
	        	double alturaInicial = altitudesInterpoladas.value(posicion / 1000.0);
	        	double desnivel = alturaFinal - alturaInicial;       	
	        	double pendiente = Math.round(((float)desnivel * 1000.0 / (float)longitud)) / 1000.0;
	        	double coeficiente = UtilidadesMath.calculoCoeficiente(pendiente * 100)*longitud / intervalo;
		        	
	        	
	        	segmento.setAlturaInicial(alturaInicial);
	        	segmento.setAlturaFinal(alturaFinal);
	        	segmento.setCoeficiente(coeficiente);
	        	segmento.setDesnivel(desnivel);
	        	segmento.setLongitud(longitud);
	        	segmento.setPendiente(pendiente);
	        	segmentos.add(segmento);
	        	
	        	posicion += intervalo;
	        }	
		} catch (NumberIsTooSmallException e) {
			//pocos datos, no hay segmentos
		}
        return segmentos;
	}

	private void setDatosGrafica(EjeX ejeX)  {
		DatosTodasGraficasBean datosGraficasBean = new DatosTodasGraficasBean();
    	List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
    	for (int i = 0; i< puntos.size(); i++)  {
    		TrackPoint punto = puntos.get(i);
        	double valorX = getValorX(punto, ejeX);
        	if (punto.getAltitud() > 0 || getDatosSegmentoActual().getAltitudMed() == 0) datosGraficasBean.getDatosAltitud().addDato(valorX, punto.getAltitud());
        	datosGraficasBean.getDatosHR().addDato(valorX, punto.getHR());
        	datosGraficasBean.getDatosPendiente().addDato(valorX, punto.getPendienteBruta() * 100.0);
        	datosGraficasBean.getDatosVelocidad().addDato(valorX, punto.getVelocidad());
        	datosGraficasBean.getDatosCadencia().addDato(valorX, punto.getCadencia());
        	datosGraficasBean.getDatosPaso().addDato(valorX, punto.getPaso() / 60000);
        	datosGraficasBean.getDatosPotencia().addDato(valorX, punto.getPotencia());
    	}
		if (ejeX.isDistancia()) datosGraficasDistancia = datosGraficasBean;
		else if (ejeX.isTiempoAbsoluto()) datosGraficasTiempoAbs = datosGraficasBean;
		else if (ejeX.isTiempoMovimiento()) datosGraficasTiempoMov = datosGraficasBean;
		else datosGraficasHora = datosGraficasBean;		
	}

	public void borraSegmento() throws Exception {
		GPSEdit.logger.debug("borraSegmento()");
		int numPuntos = actividadBean.getFinRango() - actividadBean.getInicioRango() + 1;
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		if (numPuntos > puntos.size()-2 ) throw new Exception("Segmento demasiado grande");
		GPSEdit.logger.debug("iniciamos borrado");
		TrackPoint puntoInicio = puntos.get(actividadBean.getInicioRango());
		TrackPoint puntoFin = puntos.get(actividadBean.getFinRango());
		//calculamos la diferencia real en distancia entre el primer punto borrado y el último
		double distanciaNueva = UtilidadesMath.calculoDistancia(puntoInicio.getLatitud(), puntoInicio.getLongitud(), puntoFin.getLatitud(), puntoFin.getLongitud());
		//obtenemos la diferencia medida en distancia entre los mismos puntos (usamos la distancia en vez de la medida por si uno de esos puntos no tuviera medida pero el track si 
		double distanciaAntigua = puntoFin.getDistancia() - puntoInicio.getDistancia();
		
		double decrementoDistancia = distanciaAntigua - distanciaNueva;
		//solo puede ser positivo, no tiene sentido que ahora aumente la distancia
		if (decrementoDistancia < 0) decrementoDistancia  = 0;
		
		
		for (int index = 0; index < numPuntos; index++)  {
			actividadBean.getTrack().removePunto(actividadBean.getTrack().getPuntos().get(actividadBean.getInicioRango()));
		}
		
		for (int index = actividadBean.getInicioRango(); index < puntos.size(); index++)  {
			//decrementamos la distancia MEDIDA (solo la medida, ya que la calculada se recalculará sola al actualizar el track)
			//solo aplicamos el decremento si hay distancia pero eso se compruaba ya al ver que sale negativo
			TrackPoint punto = puntos.get(index);
			double nuevaDistanciaLeida = punto.getDistanciaLeida() - decrementoDistancia;
			if (nuevaDistanciaLeida > 0) punto.setDistanciaLeida(nuevaDistanciaLeida);
		}
	}

	/**
	 * Borra los datos de HR del segmento actual de un track
	 */
	public void borraHR() {
		setHR(0);
	}

	/**
	 * Borra los datos de Cadencia del segmento actual de un track
	 */
	public void borraCadencia() {
		setCadencia(0);
	}

	/**
	 * Borra los datos de Altitud del segmento actual de un track
	 */
	public void borraAltitud() {
		setAltitud(0);
	}
	
	/**
	 * Borra los datos de Potencia del segmento actual de un track
	 */
	public void borraPotencia() {
		setPotencia(0);
	}
	
	/**
	 * Borra los datos de Velocidad del segmento actual de un track. Se recalcularán las velocidades a partir de los datos GPS
	 */
	public void borraVelocidad() {
		setVelocidad(0);
	
	}
	
	/**
	 * Borra los datos de paradas del segmento actual de un track. 
	 */
	public void borraParadas() {
		long anteriorHoraFinRango = getPuntoFinRango().getHora().getTime();
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		long horaInicio = getPuntoInicioRango().getHora().getTime();
		long tiempoMovInicio = getPuntoInicioRango().getTiempoMovimiento();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			TrackPoint punto = puntos.get(index);
			if (index > getInicioRango()) {
				long incTiempo = punto.getTiempoMovimiento() - tiempoMovInicio;
				punto.setHora(new Date(horaInicio + incTiempo));
			}
		}
		long incrementoTiempo = getPuntoFinRango().getHora().getTime() - anteriorHoraFinRango;
		for (int index = getFinRango() + 1; index < puntos.size(); index++)  {
			TrackPoint punto = puntos.get(index);
			punto.setHora(new Date(punto.getHora().getTime() + incrementoTiempo));
		}	
	}
	
	/**
	 * Borra los datos de GPS del segmento actual de un track
	 */
	public void borraGPS() {
		GPSEdit.logger.debug("borraGPS(): ");
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			puntos.get(index).setLatitud(0);
			puntos.get(index).setLongitud(0);
		}
	}
	
	/**
	 * Establece los datos de HR del segmento actual de un track a un valor fijo
	 */
	public void setHR(long hr) {
		GPSEdit.logger.debug("setHR(): " + hr);
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			puntos.get(index).setHR(hr);
		}
	}
	
	/**
	 * Establece los datos de cadencia del segmento actual de un track a un valor fijo
	 */
	public void setCadencia(long cadencia) {
		GPSEdit.logger.debug("setCadencia(): " + cadencia);
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			puntos.get(index).setCadencia(cadencia);
		}
	}
	
	/**
	 * Establece los datos de altitud del segmento actual de un track a un valor fijo
	 */
	public void setAltitud(double altitud) {
		GPSEdit.logger.debug("setAltitud(): " + altitud);
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			puntos.get(index).setAltitud(altitud);
		}
	}
	
	/**
	 * Establece los datos de altitud del segmento actual de un track a un valor fijo
	 */
	public void setPotencia(long potencia) {
		GPSEdit.logger.debug("setPotencia(): " + potencia);
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			puntos.get(index).setPotencia(potencia);
		}
	}	
	
	/**
	 * Establece los datos de velocidad del segmento actual de un track a un valor fijo
	 * También cambia los datos de distancia calculandolos en base a la velocidad
	 * Se recalculan los puntos a partir del fin del rango para añadir la diferencia de distancia a partir de ese punto
	 */
	public void setVelocidad(double velocidad) {
		long anteriorHoraFinRango = getPuntoFinRango().getHora().getTime();
		List<TrackPoint> puntos = actividadBean.getTrack().getPuntos();
		double distanciaAnterior = 0;
		long hora = getPuntoInicioRango().getHora().getTime();
		for (int index = getInicioRango(); index <= getFinRango(); index++)  {
			TrackPoint punto = puntos.get(index);
			double distancia = punto.getDistancia();
			if (index > getInicioRango()) {
				long incTiempo = Math.round( ((double)distancia - (double)distanciaAnterior) * 3600.0 / velocidad);
				hora += incTiempo;
				punto.setHora(new Date(hora));
			}
			punto.setDistanciaLeida(distancia);
			punto.setVelocidadLeida(velocidad);
			distanciaAnterior = distancia;
		}
		long incrementoTiempo = getPuntoFinRango().getHora().getTime() - anteriorHoraFinRango;
		for (int index = getFinRango() + 1; index < puntos.size(); index++)  {
			TrackPoint punto = puntos.get(index);
			punto.setHora(new Date(punto.getHora().getTime() + incrementoTiempo));
		}
	}
	
	
	
	public void resetDatosGraficas()  {
		datosGraficasDistancia = null;
		datosGraficasTiempoMov = null;
		datosGraficasTiempoAbs = null;
		datosGraficasHora = null;		
		seccionesAutomaticas = null;
	}
	
	public void updateConfiguracion() {
		seccionesAutomaticas = null;
	}
	
	public DatosSegmentoBean getDatosSegmentoActual()  {
		DatosSegmentoBean datosSegmento = getDatos(getInicioRango(), getFinRango());
    	if (datosSegmento.getAltitudMed() > 0) datosSegmento.setCoeficienteAPM(getCoeficienteAPMRango());
    	return datosSegmento;
	}

	public ActividadBean getActividadBean() {
		return actividadBean;
	}
	
	public void setTrack(Track track)  {
		actividadBean.setTrack(track);
		resetDatosGraficas();
	}

	public void setActividadBean(ActividadBean actividadBean) {
		this.actividadBean = actividadBean;
		resetDatosGraficas();
	}


	public DatosSegmentoBean getDatosTrack() {
		return datosTrack;
	}
	
}

package aplicaciones.gpsedit.beans;

public class DatosTodasGraficasBean {

	private DatosGraficaBean datosAltitud;
	private DatosGraficaBean datosHR;
	private DatosGraficaBean datosVelocidad;
	private DatosGraficaBean datosCadencia;
	private DatosGraficaBean datosPendiente;
	private DatosGraficaBean datosPotencia;
	private DatosGraficaBean datosPaso;
	
	public DatosTodasGraficasBean()  {
		datosAltitud = new DatosGraficaBean();
		datosHR = new DatosGraficaBean();
		datosVelocidad = new DatosGraficaBean();
		datosCadencia = new DatosGraficaBean();
		datosPendiente = new DatosGraficaBean();
		datosPaso = new DatosGraficaBean();
		datosPotencia = new DatosGraficaBean();
	}

	public DatosGraficaBean getDatosPaso() {
		return datosPaso;
	}

	public DatosGraficaBean getDatosAltitud() {
		return datosAltitud;
	}

	public DatosGraficaBean getDatosHR() {
		return datosHR;
	}

	public DatosGraficaBean getDatosVelocidad() {
		return datosVelocidad;
	}

	public DatosGraficaBean getDatosCadencia() {
		return datosCadencia;
	}

	public DatosGraficaBean getDatosPendiente() {
		return datosPendiente;
	}

	public DatosGraficaBean getDatosPotencia() {
		return datosPotencia;
	}
}

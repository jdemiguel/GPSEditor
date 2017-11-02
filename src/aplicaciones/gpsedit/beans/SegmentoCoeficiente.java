package aplicaciones.gpsedit.beans;


public class SegmentoCoeficiente {

	private double longitud;
	private double pendiente;
	private double desnivel;
	private double coeficiente;
	private double alturaInicial;
	private double alturaFinal;

	public double getPendiente() {
		return pendiente;
	}
	public double getDesnivel() {
		return desnivel;
	}
	public double getCoeficiente() {
		return coeficiente;
	}
	public double getAlturaInicial() {
		return alturaInicial;
	}
	public double getAlturaFinal() {
		return alturaFinal;
	}
	public void setPendiente(double pendiente) {
		this.pendiente = pendiente;
	}
	public void setDesnivel(double desnivel) {
		this.desnivel = desnivel;
	}
	public void setCoeficiente(double coeficiente) {
		this.coeficiente = coeficiente;
	}
	public void setAlturaInicial(double alturaInicial) {
		this.alturaInicial = alturaInicial;
	}
	public void setAlturaFinal(double alturaFinal) {
		this.alturaFinal = alturaFinal;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	
}

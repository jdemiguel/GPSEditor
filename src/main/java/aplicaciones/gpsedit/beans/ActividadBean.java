package aplicaciones.gpsedit.beans;

public class ActividadBean {

	private Track track;
	private int inicioRango;
	private int finRango;
	private int puntoSeleccionado;
	
	public Track getTrack() {
		return track;
	}
	public void setTrack(Track track) {
		this.track = track;
	}
	public int getInicioRango() {
		return inicioRango;
	}
	public void setInicioRango(int inicioRango) {
		this.inicioRango = inicioRango;
	}
	public int getFinRango() {
		return finRango;
	}
	public void setFinRango(int finRango) {
		this.finRango = finRango;
	}
	public int getPuntoSeleccionado() {
		return puntoSeleccionado;
	}
	public void setPuntoSeleccionado(int puntoSeleccionado) {
		this.puntoSeleccionado = puntoSeleccionado;
	}
	
	public ActividadBean clone() {
		ActividadBean actividadBean = new ActividadBean();
		actividadBean.setFinRango(finRango);
		actividadBean.setInicioRango(inicioRango);
		actividadBean.setPuntoSeleccionado(puntoSeleccionado);
		actividadBean.setTrack(track.clone());
		return actividadBean;
	}
}

package aplicaciones.gpsedit.beans;

import java.util.ArrayList;
import java.util.List;

public class Seccion {
	private List<TrackPoint> puntos =  new ArrayList<TrackPoint>();
	private DatosSegmentoBean datos = new DatosSegmentoBean();
	private int inicioRango;
	private int finRango;
	private String texto;
	private String triggerMethod;
	private String intensidad;
	
	public List<TrackPoint> getPuntos() {
		return puntos;
	}
	public void addPunto(TrackPoint punto)  {
		puntos.add(punto);
	}
	
	public DatosSegmentoBean getDatos() {
		return datos;
	}
	public void setDatos(DatosSegmentoBean datos) {
		this.datos = datos;
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
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTriggerMethod() {
		return triggerMethod;
	}
	public String getIntensidad() {
		return intensidad;
	}
	public void setTriggerMethod(String triggerMethod) {
		this.triggerMethod = triggerMethod;
	}
	public void setIntensidad(String intensidad) {
		this.intensidad = intensidad;
	}
	public void removePunto(TrackPoint punto) {
		puntos.remove(punto);
		if (finRango >= puntos.size()) finRango = puntos.size() - 1;
	}
	
	public int size() {
		return puntos.size();
	}
	
	public void borraUltimo()  {
		if (puntos.size() > 0) removePunto(puntos.get(puntos.size() - 1));
	}
	
	public Seccion clone()  {
		Seccion seccion = new Seccion();
		
		seccion.setInicioRango(inicioRango);
		seccion.setFinRango(finRango);
		seccion.setIntensidad(intensidad);
		seccion.setTexto(texto);
		seccion.setTriggerMethod(triggerMethod);
		seccion.setDatos(datos.clone());

		for (TrackPoint punto:puntos) seccion.addPunto(punto.clone());
		return seccion;
	}

}

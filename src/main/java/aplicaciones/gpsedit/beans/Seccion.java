package aplicaciones.gpsedit.beans;

import java.util.ArrayList;
import java.util.List;

public class Seccion {
	private List<TrackPoint> puntos =  new ArrayList<TrackPoint>();
	private int inicioRango;
	private int finRango;
	private String texto;
	private String triggerMethod;
	private String intensidad;
	
	private long tiempoTotal;
	private double longitud;
	private double velocidadMaxima;
	private double velocidadMed;
	private long HRMed;
	private long HRMax;
	private long cadenciaMed;

	
	public List<TrackPoint> getPuntos() {
		return puntos;
	}
	public void addPunto(TrackPoint punto)  {
		puntos.add(punto);
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
	
	public long getTiempoTotal() {
		return tiempoTotal;
	}
	public double getLongitud() {
		return longitud;
	}
	public double getVelocidadMaxima() {
		return velocidadMaxima;
	}
	public double getVelocidadMed() {
		return velocidadMed;
	}
	public long getHRMed() {
		return HRMed;
	}
	public long getHRMax() {
		return HRMax;
	}
	public long getCadenciaMed() {
		return cadenciaMed;
	}
	public void setTiempoTotal(long tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public void setVelocidadMaxima(double velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}
	public void setVelocidadMed(double velocidadMed) {
		this.velocidadMed = velocidadMed;
	}
	public void setHRMed(long hRMed) {
		HRMed = hRMed;
	}
	public void setHRMax(long hRMax) {
		HRMax = hRMax;
	}
	public void setCadenciaMed(long cadenciaMed) {
		this.cadenciaMed = cadenciaMed;
	}
	public Seccion clone()  {
		Seccion seccion = new Seccion();
		
		seccion.setInicioRango(inicioRango);
		seccion.setFinRango(finRango);
		seccion.setIntensidad(intensidad);
		seccion.setTexto(texto);
		seccion.setTriggerMethod(triggerMethod);
		
		seccion.setCadenciaMed(cadenciaMed);
		seccion.setHRMax(HRMax);
		seccion.setHRMed(HRMed);
		seccion.setLongitud(longitud);
		seccion.setTiempoTotal(tiempoTotal);
		seccion.setVelocidadMaxima(velocidadMaxima);
		seccion.setVelocidadMed(velocidadMed);

		for (TrackPoint punto:puntos) seccion.addPunto(punto.clone());
		return seccion;
	}

}

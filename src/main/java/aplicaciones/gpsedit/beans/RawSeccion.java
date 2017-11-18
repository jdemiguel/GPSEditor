package aplicaciones.gpsedit.beans;

import java.util.ArrayList;
import java.util.List;

public class RawSeccion {
	private List<RawTrackPoint> puntos =  new ArrayList<RawTrackPoint>();
	private String triggerMethod;
	private String intensidad;
	
	private long tiempoTotal;
	private double longitud;
	private double velocidadMaxima;
	private double velocidadMed;
	private long HRMed;
	private long HRMax;
	private long cadenciaMed;
	
	public void addPunto(RawTrackPoint punto)  {
		puntos.add(punto);
	}
	
	public List<RawTrackPoint> getPuntos() {
		return puntos;
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

	public long getTiempoTotal() {
		return tiempoTotal;
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

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
}

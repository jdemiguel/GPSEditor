package aplicaciones.gpsedit.beans;

import java.util.Date;


public class RawTrackPoint {

	private Date tiempo;
	private double altitud;//viene en m
	private long cadencia;
	private long hr;
	private long potencia;//viene en W
	private double latitud;
	private double longitud;
	private double distancia;//viene en m
	private double velocidad;//viene en m/s
	
	public Date getTiempo() {
		return tiempo;
	}
	public void setTiempo(Date tiempo) {
		this.tiempo = tiempo;
	}
	public double getAltitud() {
		return altitud;
	}
	public void setAltitud(double altitud) {
		this.altitud = altitud;
	}
	public long getCadencia() {
		return cadencia;
	}
	public void setCadencia(long cadencia) {
		this.cadencia = cadencia;
	}
	public long getHr() {
		return hr;
	}
	public void setHr(long hr) {
		this.hr = hr;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	public long getPotencia() {
		return potencia;
	}
	public double getVelocidad() {
		return velocidad;
	}
	public void setPotencia(long potencia) {
		this.potencia = potencia;
	}
	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

}

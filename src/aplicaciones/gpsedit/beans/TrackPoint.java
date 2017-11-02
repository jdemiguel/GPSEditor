package aplicaciones.gpsedit.beans;

import java.util.Date;

public class TrackPoint {

	private long tiempoAbsoluto;
	private long tiempoMovimiento;
	private double altitud;
	private long cadencia;
	private long potencia;
	private long hr;
	private double latitud;
	private double longitud;
	private double distancia;
	private double distanciaLeida;
	private double distanciaCalculada;
	private double pendiente;
	private double velocidad;
	private double velocidadLeida;
	private double velocidadCalculada;
	private double paso;
	private double desnivel;
	private Date hora;
	
	public long getTiempoAbsoluto() {
		return tiempoAbsoluto;
	}
	public long getTiempoMovimiento() {
		return tiempoMovimiento;
	}
	public void setTiempoAbsoluto(long tiempoAbsoluto) {
		this.tiempoAbsoluto = tiempoAbsoluto;
	}
	public void setTiempoMovimiento(long tiempoMovimiento) {
		this.tiempoMovimiento = tiempoMovimiento;
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
	public double getDesnivel() {
		return desnivel;
	}
	public void setDesnivel(double desnivel) {
		this.desnivel = desnivel;
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
	public long getHR() {
		return hr;
	}
	public void setHR(long hr) {
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
	public double getPendiente() {
		return pendiente;
	}
	public void setPendiente(double pendiente) {
		this.pendiente = pendiente;
	}
	public double getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
	public double getDistanciaLeida() {
		return distanciaLeida;
	}
	public void setDistanciaLeida(double distanciaLeida) {
		this.distanciaLeida = distanciaLeida;
	}
	public double getVelocidadLeida() {
		return velocidadLeida;
	}
	public void setVelocidadLeida(double velocidadLeida) {
		this.velocidadLeida = velocidadLeida;
	}
	public double getDistanciaCalculada() {
		return distanciaCalculada;
	}
	public double getPaso() {
		return paso;
	}
	public void setPaso(double paso) {
		this.paso = paso;
	}
	public void setDistanciaCalculada(double distanciaCalculada) {
		this.distanciaCalculada = distanciaCalculada;
	}
	public long getPotencia() {
		return potencia;
	}
	public void setPotencia(long potencia) {
		this.potencia = potencia;
	}
	public double getVelocidadCalculada() {
		return velocidadCalculada;
	}
	public void setVelocidadCalculada(double velocidadCalculada) {
		this.velocidadCalculada = velocidadCalculada;
	}
	
	public TrackPoint clone()  {
		TrackPoint punto = new TrackPoint();
		punto.setAltitud(altitud);
		punto.setCadencia(cadencia);
		punto.setDesnivel(desnivel);
		punto.setDistancia(distancia);
		punto.setDistanciaCalculada(distanciaCalculada);
		punto.setDistanciaLeida(distanciaLeida);
		punto.setHora(hora);
		punto.setHR(hr);
		punto.setLatitud(latitud);
		punto.setLongitud(longitud);
		punto.setPaso(paso);
		punto.setPendiente(pendiente);
		punto.setPotencia(potencia);
		punto.setTiempoAbsoluto(tiempoAbsoluto);
		punto.setTiempoMovimiento(tiempoMovimiento);
		punto.setVelocidad(velocidad);
		punto.setVelocidadCalculada(velocidadCalculada);
		punto.setVelocidadLeida(velocidadLeida);
		return punto;
	}
}

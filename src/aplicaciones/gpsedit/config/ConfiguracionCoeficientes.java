package aplicaciones.gpsedit.config;

import java.awt.Color;

public class ConfiguracionCoeficientes {

	private int intervalo = 1000;
	private Color colorLinea = Color.BLACK;
	private Color colorRelleno = Color.GRAY;
	private boolean relleno = true; 
	private boolean suavizado = true;
	private int ajusteSuavidad = 6;
	private boolean ejeCero = false;
	private boolean mostrarLineasPrincipales = true;
	private boolean mostrarLineasSecundarias = true;
	private boolean mostrarPendiente = true;
	private boolean mostrarAltitud = true;
	private boolean mostrarLeyenda = true;
	

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	public Color getColorLinea() {
		return colorLinea;
	}

	public Color getColorRelleno() {
		return colorRelleno;
	}

	public boolean isRelleno() {
		return relleno;
	}

	public boolean isSuavizado() {
		return suavizado;
	}

	public int getAjusteSuavidad() {
		return ajusteSuavidad;
	}

	public boolean isEjeCero() {
		return ejeCero;
	}

	public boolean isMostrarLineasPrincipales() {
		return mostrarLineasPrincipales;
	}

	public boolean isMostrarLineasSecundarias() {
		return mostrarLineasSecundarias;
	}

	public boolean isMostrarPendiente() {
		return mostrarPendiente;
	}

	public void setColorLinea(Color colorLinea) {
		this.colorLinea = colorLinea;
	}

	public void setColorRelleno(Color colorRelleno) {
		this.colorRelleno = colorRelleno;
	}

	public void setRelleno(boolean relleno) {
		this.relleno = relleno;
	}

	public void setSuavizado(boolean suavizado) {
		this.suavizado = suavizado;
	}

	public void setAjusteSuavidad(int ajusteSuavidad) {
		this.ajusteSuavidad = ajusteSuavidad;
	}

	public void setEjeCero(boolean ejeCero) {
		this.ejeCero = ejeCero;
	}

	public void setMostrarLineasPrincipales(boolean mostrarLineasPrincipales) {
		this.mostrarLineasPrincipales = mostrarLineasPrincipales;
	}

	public void setMostrarLineasSecundarias(boolean mostrarLineasSecundarias) {
		this.mostrarLineasSecundarias = mostrarLineasSecundarias;
	}

	public void setMostrarPendiente(boolean mostrarPendiente) {
		this.mostrarPendiente = mostrarPendiente;
	}

	public boolean isMostrarLeyenda() {
		return mostrarLeyenda;
	}

	public void setMostrarLeyenda(boolean mostrarLeyenda) {
		this.mostrarLeyenda = mostrarLeyenda;
	}

	public boolean isMostrarAltitud() {
		return mostrarAltitud;
	}

	public void setMostrarAltitud(boolean mostrarAltitud) {
		this.mostrarAltitud = mostrarAltitud;
	}
	
}

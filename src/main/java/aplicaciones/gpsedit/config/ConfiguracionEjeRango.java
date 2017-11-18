package aplicaciones.gpsedit.config;

import java.awt.Color;

public class ConfiguracionEjeRango {
	private Color color;
	private boolean relleno; 
	private boolean suavizado = true;
	private int ajusteSuavidad = 1;
	private boolean visible = true;
	private boolean ejeCero = false;
	private boolean tipoBarra = false;
	private boolean mostrarPunto = true;
	
	public boolean isEjeCero() {
		return ejeCero;
	}


	public void setEjeCero(boolean ejeCero) {
		this.ejeCero = ejeCero;
	}


	public ConfiguracionEjeRango(Color color, boolean relleno) {
		this.color = color;
		this.relleno = relleno;
	}
	
	public ConfiguracionEjeRango(Color color, boolean tipoBarra, boolean relleno) {
		this.color = color;
		this.relleno = relleno;
		this.tipoBarra = tipoBarra;
	}

	public Color getColor() {
		return color;
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

	public void setColor(Color color) {
		this.color = color;
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


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public boolean isTipoBarra() {
		return tipoBarra;
	}


	public boolean isMostrarPunto() {
		return mostrarPunto;
	}


	public void setTipoBarra(boolean tipoBarra) {
		this.tipoBarra = tipoBarra;
	}


	public void setMostrarPunto(boolean mostrarPunto) {
		this.mostrarPunto = mostrarPunto;
	}
}

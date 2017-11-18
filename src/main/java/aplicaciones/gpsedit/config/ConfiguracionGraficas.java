package aplicaciones.gpsedit.config;

import java.awt.Color;

import aplicaciones.gpsedit.util.EjeX;

public class ConfiguracionGraficas {
	private ConfiguracionEjeRango ejeAltitud = new ConfiguracionEjeRango(Color.LIGHT_GRAY, true);
	private ConfiguracionEjeRango ejeHR = new ConfiguracionEjeRango(Color.RED, false);
	private ConfiguracionEjeRango ejeCadencia = new ConfiguracionEjeRango(Color.GREEN, false);
	private ConfiguracionEjeRango ejeVelocidad = new ConfiguracionEjeRango(Color.BLUE, false);
	private ConfiguracionEjeRango ejePaso = ejeVelocidad;
	private ConfiguracionEjeRango ejePendiente= new ConfiguracionEjeRango(Color.DARK_GRAY, false);
	private ConfiguracionEjeRango ejePotencia= new ConfiguracionEjeRango(Color.MAGENTA, false);

	private int incrementoRango = 1;
	private EjeX ejeX = EjeX.getInstanciaEjeDistancia();

	public void setIncrementoRango(int incrementoRango) {
		this.incrementoRango = incrementoRango;
	}

	public int getIncrementoRango()  {
		return incrementoRango;
	}

	public EjeX getEjeX() {
		return ejeX;
	}
	
	public ConfiguracionEjeRango getEjeAltitud() {
		return ejeAltitud;
	}

	public ConfiguracionEjeRango getEjeHR() {
		return ejeHR;
	}

	public ConfiguracionEjeRango getEjeCadencia() {
		return ejeCadencia;
	}

	public ConfiguracionEjeRango getEjeVelocidad() {
		return ejeVelocidad;
	}

	public ConfiguracionEjeRango getEjePendiente() {
		return ejePendiente;
	}

	public ConfiguracionEjeRango getEjePotencia() {
		return ejePotencia;
	}

	public ConfiguracionEjeRango getEjePaso() {
		return ejePaso;
	}
}

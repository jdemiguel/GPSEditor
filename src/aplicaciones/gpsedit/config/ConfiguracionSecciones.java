package aplicaciones.gpsedit.config;

import java.awt.Color;

import aplicaciones.gpsedit.util.EjeX;

public class ConfiguracionSecciones {
	private ConfiguracionEjeRango ejeHR = new ConfiguracionEjeRango(Color.RED, false, false);
	private ConfiguracionEjeRango ejeCadencia = new ConfiguracionEjeRango(Color.GREEN, false, false);
	private ConfiguracionEjeRango ejeVelocidad = new ConfiguracionEjeRango(Color.BLUE, true, false);
	private ConfiguracionEjeRango ejePotencia = new ConfiguracionEjeRango(Color.MAGENTA, false, false);
	
	private int intervaloSeccionAutomatica = 1;
	private EjeX ejeXSeccionAutomatica = EjeX.getInstanciaEjeDistancia();

	public ConfiguracionEjeRango getEjeHR() {
		return ejeHR;
	}

	public ConfiguracionEjeRango getEjeCadencia() {
		return ejeCadencia;
	}

	public ConfiguracionEjeRango getEjeVelocidad() {
		return ejeVelocidad;
	}
	
	public int getIntervaloSeccionAutomatica() {
		return intervaloSeccionAutomatica;
	}

	public void setIntervaloSeccionAutomatica(int intervaloSeccionAutomatica) {
		this.intervaloSeccionAutomatica = intervaloSeccionAutomatica;
	}

	public EjeX getEjeXSeccionAutomatica() {
		return ejeXSeccionAutomatica;
	}

	public ConfiguracionEjeRango getEjePotencia() {
		return ejePotencia;
	}
}

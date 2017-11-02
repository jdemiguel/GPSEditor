package aplicaciones.gpsedit.config;


public class ConfiguracionActividad {

	private ConfiguracionGraficas configuracionGraficas = new ConfiguracionGraficas();
	private ConfiguracionSecciones configuracionSecciones = new ConfiguracionSecciones();
	private ConfiguracionMapa configuracionMapa = new ConfiguracionMapa();
	private ConfiguracionCoeficientes configuracionCoeficientes = new ConfiguracionCoeficientes();

	public ConfiguracionActividad() {
	}

	public ConfiguracionSecciones getConfiguracionSecciones() {
		return configuracionSecciones;
	}

	public ConfiguracionGraficas getConfiguracionGraficas() {
		return configuracionGraficas;
	}

	public ConfiguracionMapa getConfiguracionMapa() {
		return configuracionMapa;
	}

	public ConfiguracionCoeficientes getConfiguracionCoeficientes() {
		return configuracionCoeficientes;
	}

	
}

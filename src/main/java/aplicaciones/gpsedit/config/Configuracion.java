package aplicaciones.gpsedit.config;

public class Configuracion {
	
	private ConfiguracionTrack configuracionTrack;
	private ConfiguracionActividad configuracionActividad;
	
	public ConfiguracionActividad getConfiguracionActividad() {
		return configuracionActividad;
	}

	private static Configuracion instance = new Configuracion();
	
	private Configuracion()  {
		configuracionTrack = new ConfiguracionTrack();
		configuracionActividad = new ConfiguracionActividad();

	}

	public ConfiguracionTrack getConfiguracionTrack() {
		return configuracionTrack;
	}

	public static Configuracion getInstance()  {
		return instance;
	}
	
}

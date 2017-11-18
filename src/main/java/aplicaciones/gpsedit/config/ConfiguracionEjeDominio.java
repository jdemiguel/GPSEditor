package aplicaciones.gpsedit.config;

import aplicaciones.gpsedit.util.EjeX;

public class ConfiguracionEjeDominio {
	
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

}

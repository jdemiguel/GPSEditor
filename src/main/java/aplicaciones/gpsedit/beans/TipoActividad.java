package aplicaciones.gpsedit.beans;

public enum TipoActividad {
	RUNNING ("Carrera"), 
	CICLISMO_CARRETERA ("Ciclismo de carretera"), 
	CICLISMO_MTB ("Ciclismo de montaña"), 
	CICLISMO_INTERIOR ("Ciclismo de interior"), 
	SENDERISMO ("Senderismo"), 
	ESQUI ("Esquí"), 
	NATACION ("Natación"),
	OTROS ("Otros");

	private String nombre;

	private TipoActividad (String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

	public boolean isPaso() {
		return this.equals(RUNNING);
	}
	
	public String toString()  {
		return nombre;
	}
	
}

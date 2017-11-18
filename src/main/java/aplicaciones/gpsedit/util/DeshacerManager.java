package aplicaciones.gpsedit.util;

import aplicaciones.gpsedit.beans.ActividadBean;

public class DeshacerManager {

	private Pila<ActividadBean> pilaDeshacer;
	private Pila<ActividadBean> pilaRehacer;
	
	private static DeshacerManager instance = new DeshacerManager();
	 
	private DeshacerManager() {
		pilaDeshacer = new Pila<ActividadBean>(10);
		pilaRehacer = new Pila<ActividadBean>(10);
	}
	
	public static DeshacerManager getInstance()  {
		return instance;
	}
	
	public void guardarCambio(ActividadBean cambio) {
		pilaDeshacer.push(cambio);
		pilaRehacer.clear();
	}
	
	
	public ActividadBean deshacer(ActividadBean actual) {
		pilaRehacer.push(actual);
		return pilaDeshacer.pop();
	}
	
	
	public ActividadBean rehacer(ActividadBean actual) {
		pilaDeshacer.push(actual);
		return pilaRehacer.pop();
	}
	
	public boolean isRehacer()  {
		return !pilaRehacer.empty();
	}		
	
	public boolean isDeshacer()  {
		return !pilaDeshacer.empty();
	}

	
}

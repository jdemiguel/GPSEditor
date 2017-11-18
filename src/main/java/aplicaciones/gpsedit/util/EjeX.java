package aplicaciones.gpsedit.util;

import aplicaciones.gpsedit.Constantes.EjeDominio;

public class EjeX {
	private EjeDominio ejeDominio = EjeDominio.DISTANCIA;
	
	public final static EjeX DISTANCIA = new EjeX(EjeDominio.DISTANCIA); 
	public final static EjeX TIEMPO_ABSOLUTO = new EjeX(EjeDominio.TIEMPO_ABSOLUTO); 
	public final static EjeX TIEMPO_MOVIMIENTO = new EjeX(EjeDominio.TIEMPO_MOVIMIENTO); 
	public final static EjeX HORA = new EjeX(EjeDominio.HORA);
	
	public static EjeX getInstanciaEjeDistancia()  {
		return new EjeX(EjeDominio.DISTANCIA);
	}
	
	public EjeX(EjeDominio ejeDominio) {
		this.ejeDominio = ejeDominio;
	}

	public void setDistancia()  {
		ejeDominio = EjeDominio.DISTANCIA;
	}
	public void setTiempoMovimiento()  {
		ejeDominio = EjeDominio.TIEMPO_MOVIMIENTO;
	}
	public void setTiempoAbsoluto()  {
		ejeDominio = EjeDominio.TIEMPO_ABSOLUTO;
	}
	public void setHora()  {
		ejeDominio = EjeDominio.HORA;
	}
	
	public boolean isDistancia()  {
		return ejeDominio.equals(EjeDominio.DISTANCIA);
	}
	public boolean isTiempoMovimiento()  {
		return ejeDominio.equals(EjeDominio.TIEMPO_MOVIMIENTO);
	}
	public boolean isTiempoAbsoluto()  {
		return ejeDominio.equals(EjeDominio.TIEMPO_ABSOLUTO);
	}
	public boolean isHora()  {
		return ejeDominio.equals(EjeDominio.HORA);
	}
}

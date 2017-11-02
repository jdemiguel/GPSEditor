package aplicaciones.gpsedit.config;

public class ConfiguracionTrack {
	
	private double velocidadMinima = 3;//se mide en km/h por debajo de esta consideramos que no ha habido movimiento
	private int tiempoMinimo = 10000; //medido en ms si pasa mas de ese tiempo entre punto y punto y la distancia no es suficiente para 3,6km/h le ponemos un cero
	private int distanciaMinima = 10; //medido en ms si pasa mas de ese tiempo entre punto y punto y la distancia no es suficiente para 3,6km/h le ponemos un cero
	
	private double cambioMaximoPendiente = 0.06f; //medido en % es el máximo incremento o decremento de pendiente entre punto y punto. Si se supera eliminamos el punto 
	
	
	public double getCambioMaximoPendiente() {
		return cambioMaximoPendiente;
	}
	public void setCambioMaximoVelocidad(double cambioMaximoPendiente) {
		this.cambioMaximoPendiente = cambioMaximoPendiente;
	}
	public double getVelocidadMinima() {
		return velocidadMinima;
	}
	public void setVelocidadMinima(double velocidadMinima) {
		this.velocidadMinima = velocidadMinima;
	}
	public int getTiempoMinimo() {
		return tiempoMinimo;
	}
	public void setTiempoMinimo(int tiempoMinimo) {
		this.tiempoMinimo = tiempoMinimo;
	}
	
	public int getDistanciaMinima() {
		return distanciaMinima;
	}
	public void setDistanciaMinima(int distanciaMinima) {
		this.distanciaMinima = distanciaMinima;
	}
	
}

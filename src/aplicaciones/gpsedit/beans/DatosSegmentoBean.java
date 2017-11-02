package aplicaciones.gpsedit.beans;

import java.util.Date;

public class DatosSegmentoBean {

	private long distanciaInicial;
	private long distanciaFinal;
	private long tiempoInicial;
	private long tiempoFinal;
	private Date horaInicio;
	private Date horaFin;
	private double desnivelAcumulado;
	private double desnivelTotal;
	private double longitud;
	private long tiempoMovimiento;
	private long tiempoAbsoluto;
	private long cadenciaMed;
	private long cadenciaMax;
	private long cadenciaMin;
	private long hrMed;
	private long hrMax;
	private long hrMin;
	private double velocidadMed;
	private double velocidadMax;
	private double velocidadMin;
	private double pendienteMed;
	private double pendienteMax;
	private double pendienteMin;
	private double altitudMed;
	private double altitudMax;
	private double altitudMin;
	private double pasoMed;
	private double pasoMax;
	private double pasoMin;	
	private long potenciaMed;
	private long potenciaMax;
	private long potenciaMin;
	private double coeficienteAPM;
	
	public long getDistanciaInicial() {
		return distanciaInicial;
	}
	public double getPasoMed() {
		return pasoMed;
	}
	public double getPasoMax() {
		return pasoMax;
	}
	public double getPasoMin() {
		return pasoMin;
	}
	public void setPasoMed(double pasoMed) {
		this.pasoMed = pasoMed;
	}
	public void setPasoMax(double pasoMax) {
		this.pasoMax = pasoMax;
	}
	public void setPasoMin(double pasoMin) {
		this.pasoMin = pasoMin;
	}
	public void setDistanciaInicial(long distanciaInicial) {
		this.distanciaInicial = distanciaInicial;
	}
	public long getDistanciaFinal() {
		return distanciaFinal;
	}
	public void setDistanciaFinal(long distanciaFinal) {
		this.distanciaFinal = distanciaFinal;
	}
	public long getTiempoInicial() {
		return tiempoInicial;
	}
	public void setTiempoInicial(long tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}
	public long getTiempoFinal() {
		return tiempoFinal;
	}
	public void setTiempoFinal(long tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}
	public double getDesnivelAcumulado() {
		return desnivelAcumulado;
	}
	public void setDesnivelAcumulado(double desnivelAcumulado) {
		this.desnivelAcumulado = desnivelAcumulado;
	}
	public double getDesnivelTotal() {
		return desnivelTotal;
	}
	public void setDesnivelTotal(double desnivelTotal) {
		this.desnivelTotal = desnivelTotal;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public long getTiempoMovimiento() {
		return tiempoMovimiento;
	}
	public void setTiempoMovimiento(long tiempoMovimiento) {
		this.tiempoMovimiento = tiempoMovimiento;
	}
	public long getTiempoAbsoluto() {
		return tiempoAbsoluto;
	}
	public void setTiempoAbsoluto(long tiempoAbsoluto) {
		this.tiempoAbsoluto = tiempoAbsoluto;
	}
	public long getCadenciaMed() {
		return cadenciaMed;
	}
	public void setCadenciaMed(long cadenciaMed) {
		this.cadenciaMed = cadenciaMed;
	}
	public long getCadenciaMax() {
		return cadenciaMax;
	}
	public void setCadenciaMax(long cadenciaMax) {
		this.cadenciaMax = cadenciaMax;
	}
	public long getCadenciaMin() {
		return cadenciaMin;
	}
	public void setCadenciaMin(long cadenciaMin) {
		this.cadenciaMin = cadenciaMin;
	}
	public long getHrMed() {
		return hrMed;
	}
	public void setHrMed(long hrMed) {
		this.hrMed = hrMed;
	}
	public long getHrMax() {
		return hrMax;
	}
	public void setHrMax(long hrMax) {
		this.hrMax = hrMax;
	}
	public long getHrMin() {
		return hrMin;
	}
	public void setHrMin(long hrMin) {
		this.hrMin = hrMin;
	}
	public double getVelocidadMed() {
		return velocidadMed;
	}
	public void setVelocidadMed(double velocidadMed) {
		this.velocidadMed = velocidadMed;
	}
	public double getVelocidadMax() {
		return velocidadMax;
	}
	public void setVelocidadMax(double velocidadMax) {
		this.velocidadMax = velocidadMax;
	}
	public double getVelocidadMin() {
		return velocidadMin;
	}
	public void setVelocidadMin(double velocidadMin) {
		this.velocidadMin = velocidadMin;
	}
	public double getPendienteMed() {
		return pendienteMed;
	}
	public void setPendienteMed(double pendienteMed) {
		this.pendienteMed = pendienteMed;
	}
	public double getPendienteMax() {
		return pendienteMax;
	}
	public void setPendienteMax(double pendienteMax) {
		this.pendienteMax = pendienteMax;
	}
	public double getPendienteMin() {
		return pendienteMin;
	}
	public void setPendienteMin(double pendienteMin) {
		this.pendienteMin = pendienteMin;
	}
	public double getAltitudMed() {
		return altitudMed;
	}
	public void setAltitudMed(double altitudMed) {
		this.altitudMed = altitudMed;
	}
	public double getAltitudMax() {
		return altitudMax;
	}
	public void setAltitudMax(double altitudMax) {
		this.altitudMax = altitudMax;
	}
	public double getAltitudMin() {
		return altitudMin;
	}
	public void setAltitudMin(double altitudMin) {
		this.altitudMin = altitudMin;
	}
	public long getPotenciaMed() {
		return potenciaMed;
	}
	public long getPotenciaMax() {
		return potenciaMax;
	}
	public long getPotenciaMin() {
		return potenciaMin;
	}
	public void setPotenciaMed(long potenciaMed) {
		this.potenciaMed = potenciaMed;
	}
	public void setPotenciaMax(long potenciaMax) {
		this.potenciaMax = potenciaMax;
	}
	public void setPotenciaMin(long potenciaMin) {
		this.potenciaMin = potenciaMin;
	}
	public double getCoeficienteAPM() {
		return coeficienteAPM;
	}
	public void setCoeficienteAPM(double coeficienteAPM) {
		this.coeficienteAPM = coeficienteAPM;
	}
	
	public DatosSegmentoBean clone()  {
		DatosSegmentoBean datos = new DatosSegmentoBean();
		datos.setAltitudMax(altitudMax);
		datos.setAltitudMed(altitudMed);
		datos.setAltitudMin(altitudMin);
		datos.setCadenciaMax(cadenciaMax);
		datos.setCadenciaMed(cadenciaMed);
		datos.setCadenciaMin(cadenciaMin);
		datos.setCoeficienteAPM(coeficienteAPM);
		datos.setDesnivelAcumulado(desnivelAcumulado);
		datos.setDesnivelTotal(desnivelTotal);
		datos.setDistanciaFinal(distanciaFinal);
		datos.setDistanciaInicial(distanciaInicial);
		datos.setHoraFin(horaFin);
		datos.setHoraInicio(horaInicio);
		datos.setHrMax(hrMax);
		datos.setHrMed(hrMed);
		datos.setHrMin(hrMin);
		datos.setLongitud(longitud);
		datos.setPasoMax(pasoMax);
		datos.setPasoMed(pasoMed);
		datos.setPasoMin(pasoMin);
		datos.setPendienteMax(pendienteMax);
		datos.setPendienteMed(pendienteMed);
		datos.setPendienteMin(pendienteMin);
		datos.setPotenciaMax(potenciaMax);
		datos.setPotenciaMed(potenciaMed);
		datos.setPotenciaMin(potenciaMin);
		datos.setTiempoAbsoluto(tiempoAbsoluto);
		datos.setTiempoFinal(tiempoFinal);
		datos.setTiempoInicial(tiempoInicial);
		datos.setTiempoMovimiento(tiempoMovimiento);
		datos.setVelocidadMax(velocidadMax);
		datos.setVelocidadMed(velocidadMed);
		datos.setVelocidadMin(velocidadMin);
		return datos;
	}
	
}

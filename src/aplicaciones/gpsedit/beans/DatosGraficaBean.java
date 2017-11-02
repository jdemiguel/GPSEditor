package aplicaciones.gpsedit.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

import aplicaciones.gpsedit.GPSEdit;

public class DatosGraficaBean {
    List<Double> ejeX;
    List<Double> valores;
    
	private UnivariateFunction datosBrutos;
	private UnivariateFunction datosSuavizados[];
    
	public DatosGraficaBean()  {
		this.datosSuavizados = new UnivariateFunction[10];
		this.datosBrutos = null;
		ejeX = new ArrayList<Double>();
		valores = new ArrayList<Double>();
	}
	
	public void addDato(double x, double valor)  {
		//solo lo añadimos si ha aumentado la ordenada para que no de error
		if (ejeX.size() == 0 || x > ejeX.get(ejeX.size() - 1))  {
			ejeX.add(x);
			valores.add(valor);
		}
	}
	
	private double[] getEjeX()  {
		return ArrayUtils.toPrimitive(ejeX.toArray(new Double[ejeX.size()]));
	}

	private double[] getValores()  {
		return ArrayUtils.toPrimitive(valores.toArray(new Double[valores.size()]));
	}
	
	public UnivariateFunction getDatosSuavizados(int suavidad) {
		if (this.datosSuavizados[suavidad] == null) {
			double bandwidth = 1f / Math.pow(2, (double)(10.5 - (double)suavidad/2));
			UnivariateInterpolator interpoladorLoess = new LoessInterpolator(bandwidth, 0 );
			try {
				this.datosSuavizados[suavidad] = interpoladorLoess.interpolate(getEjeX(), getValores());
			} catch (Exception e)  {
				GPSEdit.logger.debug("Error en interpolador: bajamos" + suavidad, e);
				if (suavidad == 0) this.datosSuavizados[suavidad] = getDatosBrutos();
				else this.datosSuavizados[suavidad] = getDatosSuavizados(suavidad - 1);
			}
		}
		return this.datosSuavizados[suavidad];
	}
	
	public UnivariateFunction getDatosBrutos() {
		if (this.datosBrutos == null) {
			UnivariateInterpolator interpolador = new LinearInterpolator();
			this.datosBrutos = interpolador.interpolate(getEjeX(), getValores());
		}
		return this.datosBrutos;
	}
	
}

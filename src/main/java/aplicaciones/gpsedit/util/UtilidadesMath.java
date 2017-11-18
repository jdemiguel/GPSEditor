package aplicaciones.gpsedit.util;

public class UtilidadesMath {
	/**
     * Utiliza la fórmula de Haverside para el cálculo de la distancia entre dos puntos. Esta fórmula supone la tierra como una esfera perfecta
     * @param latInicial
     * @param longInicial
     * @param latFinal
     * @param longFinal
     * @return
     */
	public static double calculoDistancia(double latInicial, double longInicial, double latFinal, double longFinal) {  
       double dLat = Math.toRadians(latFinal-latInicial);  
       double dLon = Math.toRadians(longFinal-longInicial);  
       double a = Math.sin(dLat/2) * Math.sin(dLat/2) +  
          Math.cos(Math.toRadians(latInicial)) * Math.cos(Math.toRadians(latFinal)) *  
          Math.sin(dLon/2) * Math.sin(dLon/2);  
       double c = 2 * Math.asin(Math.sqrt(a));  
       return 6371000 * c;  
    } 
	
	
	/**
	 * Calcula el coeficiente APM de la web altimetrías.net
	 * @param pendiente
	 * @return
	 */
	public static double calculoCoeficiente(double pendiente)  {
		double coeficiente = 1;

		if (pendiente > 0)  {
			if (pendiente < 4) {
				coeficiente += pendiente;
			} else{
				coeficiente += 4;
				double resto = pendiente - 4;
				int factor = 2;
				while (resto > 0){
					if (resto < 1) coeficiente += resto*factor;
					else coeficiente += factor;
					resto = resto -1;
					factor++;
				}
			}
		}
		return coeficiente;
	}
	/**
	 * Redondea a un número concreto de decimales (no formatea, devuelve un número)
	 * @param entrada
	 * @param numDecimales
	 * @return
	 */
	public static double round(double entrada, int numDecimales)  {
		double multiplicador = Math.pow(10.0, numDecimales);
		return (double)Math.round(entrada * multiplicador) / multiplicador;
	}
}

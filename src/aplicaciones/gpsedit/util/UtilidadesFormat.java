package aplicaciones.gpsedit.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UtilidadesFormat {
	private static NumberFormat percentFormat =  NumberFormat.getPercentInstance();
	private static NumberFormat decimalFormat =  NumberFormat.getIntegerInstance();
	private static NumberFormat integerFormat =  NumberFormat.getIntegerInstance();
	private static NumberFormat floatFormat =  NumberFormat.getNumberInstance();
	private static NumberFormat doubleFormat =  NumberFormat.getNumberInstance();
    private static SimpleDateFormat pasoFormat = new SimpleDateFormat ("mm:ss");
    private static SimpleDateFormat tiempoFormat = new SimpleDateFormat ("HH:mm:ss");
	private static SimpleDateFormat fechaFormat = new SimpleDateFormat ("dd/MM/yy HH:mm");
	private static SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat horaCompletaFormat = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat msFormat = new SimpleDateFormat ("HH:mm:ss.S");

	static  {
	    percentFormat.setMaximumFractionDigits(1);
	    decimalFormat.setMaximumFractionDigits(1);
	    integerFormat.setMaximumFractionDigits(0);
	    floatFormat.setMinimumFractionDigits(0);
	    floatFormat.setMaximumFractionDigits(3);
	    doubleFormat.setMinimumFractionDigits(0);
	    doubleFormat.setMaximumFractionDigits(9);
	    tiempoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    pasoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    horaFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	public static NumberFormat getPercentFormat() {
		return percentFormat;
	}

	public static NumberFormat getDecimalFormat() {
		return decimalFormat;
	}

	public static NumberFormat getIntegerFormat() {
		return integerFormat;
	}

	public static NumberFormat getFloatFormat() {
		return floatFormat;
	}

	public static SimpleDateFormat getTiempoFormat() {
		return tiempoFormat;
	}

	public static SimpleDateFormat getFechaFormat() {
		return fechaFormat;
	}

	public static NumberFormat getDoubleFormat() {
		return doubleFormat;
	}

	public static SimpleDateFormat getPasoFormat() {
		return pasoFormat;
	}

	public static SimpleDateFormat getHoraFormat() {
		return horaFormat;
	}

	public static SimpleDateFormat getMsFormat() {
		return msFormat;
	}

	public static SimpleDateFormat getHoraCompletaFormat() {
		return horaCompletaFormat;
	}
	

}

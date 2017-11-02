package aplicaciones.gpsedit.gps;

public class GPSParserFactory {

	public static GPSParser getParser(String tipo)  {
		if (tipo.equalsIgnoreCase(GPSParser.GPX)) return new GPSParserGPX();
		if (tipo.equalsIgnoreCase(GPSParser.TCX)) return new GPSParserTCX();
		return null;
	}
	
}

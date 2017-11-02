package aplicaciones.gpsedit.util;

import java.io.File;
import java.io.FileWriter;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import aplicaciones.gpsedit.excepciones.ExtensionInvalidaException;
import aplicaciones.gpsedit.gps.GPSParser;

public class UtilidadesFicheros {

	
	public static String getTipo(File fichero) throws ExtensionInvalidaException  {
		try  {
			String tipo = fichero.getAbsolutePath().substring(fichero.getAbsolutePath().lastIndexOf('.') + 1);
			if (tipo.equalsIgnoreCase(GPSParser.GPX)) return GPSParser.GPX;
			if (tipo.equalsIgnoreCase(GPSParser.TCX)) return GPSParser.TCX;
		} catch (Exception e)  {}
		throw new ExtensionInvalidaException("La extensión del fichero no es válida, sólo están permitido ficheros GPX o TCX");
	}
	
	public static void xmlToFile(Document document, File fichero) throws Exception {
		FileWriter fw = null;
		OutputFormat format = new OutputFormat(document, "UTF-8", true);
		fw = new FileWriter(fichero);
		XMLSerializer serial = new XMLSerializer( fw, format );
		serial.asDOMSerializer();
		serial.serialize(document.getDocumentElement());
		fw.close();
	}
	
	
}

package aplicaciones.gpsedit.util;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		StreamResult streamResult = new StreamResult(fichero);
		transformer.transform(new DOMSource(document), streamResult);		
	}
	
	
}

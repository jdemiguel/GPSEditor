package aplicaciones.gpsedit.util;



import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import aplicaciones.gpsedit.GPSEdit;


public class UtilXML  {
 
	public static Document stringToXml(String xml) {
	        try  {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();	
			ByteArrayInputStream byteStream = new ByteArrayInputStream(xml.getBytes());
			Document document = documentBuilder.parse(byteStream);
			return document;
		} catch (Exception e)	{
			GPSEdit.logger.error("error al convertir string a xml:" + e.toString());
			return null;
		}
	
	}

	public static String xmlToString(Document document) {
		try	{
		TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(writer));
			return writer.getBuffer().toString();
		} catch (Exception e)	{
			GPSEdit.logger.error("error al convertir xml a string:" + e.toString());
			return "";
		}
	}
	
	
	/*
	public static Document fileToXml(String nombre) {
	        try  {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();	
			FileInputStream fileStream = new FileInputStream(nombre);
			Document document = documentBuilder.parse(fileStream);
			return document;
		} catch (Exception e)	{
			GPSEdit.logger.error("error al convertir string a xml:" + e.toString());
			return null;
		}
	
	}
*/
    
}


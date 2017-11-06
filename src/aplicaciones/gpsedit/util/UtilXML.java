package aplicaciones.gpsedit.util;



import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
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
		StringWriter strResponse = null;
		try	{
			OutputFormat format = new OutputFormat(document, "ISO-8859-1", true);
			strResponse = new StringWriter();
			XMLSerializer serial = new XMLSerializer( strResponse, format );
			serial.asDOMSerializer();
			serial.serialize(document.getDocumentElement());
			return strResponse.toString();
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


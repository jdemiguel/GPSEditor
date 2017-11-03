package aplicaciones.gpsedit.gps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.beans.Autor;
import aplicaciones.gpsedit.beans.Dispositivo;
import aplicaciones.gpsedit.beans.RawSeccion;
import aplicaciones.gpsedit.beans.RawTrack;
import aplicaciones.gpsedit.beans.RawTrackPoint;
import aplicaciones.gpsedit.beans.TipoActividad;

public abstract class GPSParser {

	public final static String GPX = "gpx";
	public final static String TCX = "tcx";
	public final static String PLANTILLA_GPX = "plantilla_gpx";
	public final static String PLANTILLA_TCX = "plantilla_tcx";
	
	
	
	public abstract DocumentBuilderFactory getFactory() throws Exception;

	public abstract NodeList getSecciones(Element nodo);
	public abstract NodeList getPuntos(Element nodo);
	public abstract double getLatitud(Element nodo);
	public abstract double getLongitud(Element nodo);
	public abstract double getAltitud(Element nodo);
	public abstract long getHR(Element nodo);
	public abstract long getCadencia(Element nodo);
	public abstract double getVelocidad(Element nodo);
	public abstract long getPotencia(Element nodo);
	public abstract double getDistancia(Element nodo);
	public abstract Date getTiempo(Element nodo) throws DOMException, ParseException;

	public abstract String getTriggerMethod(Element nodo);
	public abstract String getIntensidad(Element nodo);

	
	public abstract Autor getAutor(Element nodo) ;
	public abstract String getNombre(Element nodo);
	public abstract TipoActividad getTipoActividad(Element nodo);
	public abstract Dispositivo getDispositivo(Element nodo);
	public abstract String getDescripcion(Element nodo);
	public abstract long getCalorias(Element nodo);
	
	public abstract void insertaSecciones(Document document, RawTrack track);
	public abstract String getNombrePlantilla();
	public abstract String getTipo();
	
	public abstract void setAutor(Document document, Autor valor) ;
	public abstract void setNombre(Document document, String valor);
	public abstract void setTipoActividad(Document document, TipoActividad valor);
	public abstract void setDispositivo(Document document, Dispositivo valor);
	public abstract void setDescripcion(Document document, String valor);
	public abstract void setCalorias(Document document, long valor);
	public abstract void setTime(Document document, Date valor);
	
	public SimpleDateFormat df1;
	public SimpleDateFormat df2;

	public GPSParser()  {
		df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");	
		df1.setTimeZone(TimeZone.getTimeZone("GMT"));
		df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");	
		df2.setTimeZone(TimeZone.getTimeZone("GMT"));
		
	}
	
	public long getLongNodoNS(Element node, String tagName) {
		long valor = 0;
		try {
			valor = Math.round(Double.parseDouble(node.getElementsByTagNameNS("*",  tagName).item(0).getFirstChild().getNodeValue()));
		} catch (Exception e) {
		}
		return valor;
	}
	
	public long getLongNodo(Element node, String tagName) {
		long valor = 0;
		try {
			valor = Math.round(Double.parseDouble(node.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue()));
		} catch (Exception e) {}
		return valor;
	}

	public double getDoubleNodo(Element nodo, String tagName) {
		double valor = 0;
		try {
			valor = Double.parseDouble(nodo.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue());
		} catch (Exception e) {}
		return valor;
	}
	
	public String getStringNodo(Element nodo, String tagName) {
		String valor = "";
		try {
			valor = nodo.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
		} catch (Exception e) {}
		return valor;
	}
	
	public double getDoubleAttr(Element nodo, String attrName) {
		double valor = 0;
		try {
			valor = Double.parseDouble(nodo.getAttribute(attrName));
		} catch (Exception e) {}
		return valor;
	}
	
	/**
	 * Como a veces vienen los milisegundos y a veces no, probamos ambas posibilidades 
	 * @param nodo
	 * @param tagName
	 * @return
	 */
	public Date getDateNodo(Element nodo, String tagName) {
		Date valor = null;
		try {
			valor = df1.parse(nodo.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue());
		} catch (Exception e) {}
		if (valor == null)  {
			try {
				valor = df2.parse(nodo.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue());
			} catch (Exception e) {}
		}
		return valor;
	}	
	

	
	public void setNodeValue(Document document, Element nodoPadre, String nombre, Object valor) {
		Node nodo = nodoPadre.getElementsByTagName(nombre).item(0);
		if (valor != null)  {
			nodo.appendChild(document.createTextNode(valor.toString()));	
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}
	
	public void setNodeAtribute(Document document, Element nodoPadre, String nombre, Object valor) {
		if (valor != null)  {
			nodoPadre.setAttribute(nombre, valor.toString());	
		}
	}
	
	public RawTrack parse (File fichero) throws Exception {
		DocumentBuilderFactory factory = getFactory();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();	
		FileInputStream fileStream = new FileInputStream(fichero.getAbsolutePath());
		RawTrack track = parse(documentBuilder.parse(fileStream));
		track.setFichero(fichero);
		track.setGrabado(false);
		return track;
	}

	public RawTrack parse(Document xml) throws Exception {
		RawTrack track = new RawTrack();
		Element rootNode = xml.getDocumentElement();

		NodeList secciones = getSecciones(rootNode);
		boolean hayGPS = false;
		boolean hayCadencia = false;
		boolean hayAltitud = false;
		boolean hayHr = false;
		boolean hayPotencia = false;
		for (int i=0; i< secciones.getLength(); i++) {
			Element nodoSeccion = (Element) secciones.item(i);
			RawSeccion seccion = new RawSeccion();
			track.addSeccion(seccion);
			NodeList nodos = getPuntos(nodoSeccion);
			for (int j = 0; j< nodos.getLength(); j++)  {
				Element nodo = (Element) nodos.item(j);
				RawTrackPoint punto = new RawTrackPoint();
				try {
					punto.setLatitud(getLatitud(nodo));
					punto.setLongitud(getLongitud(nodo));
					punto.setDistancia(getDistancia(nodo));
					punto.setAltitud(getAltitud(nodo));
					punto.setHr(getHR(nodo));
					punto.setCadencia(getCadencia(nodo));
					punto.setTiempo(getTiempo(nodo));
					punto.setVelocidad(getVelocidad(nodo) * 3.6);
					punto.setPotencia(getPotencia(nodo));
					seccion.addPunto(punto);
					if (punto.getLatitud() != 0) hayGPS = true;
					if (punto.getCadencia() != 0) hayCadencia = true;
					if (punto.getAltitud() != 0) hayAltitud = true;
					if (punto.getHr() != 0) hayHr = true;
					if (punto.getPotencia() != 0) hayPotencia = true;
				} catch (Exception e) {
					GPSEdit.logger.error("Error en punto " + i, e);
					throw new Exception ("Error al leer un punto");
				}
			}
		}
		track.setAutor(getAutor(xml.getDocumentElement()));
		track.setDispositivo(getDispositivo(xml.getDocumentElement()));
		track.setNombre(getNombre(xml.getDocumentElement()));
		track.setTipoActividad(getTipoActividad(xml.getDocumentElement()));
		track.setDescripcion(getDescripcion(xml.getDocumentElement()));
		track.setCalorias(getCalorias(xml.getDocumentElement()));
		track.setGPS(hayGPS);
		track.setCadencia(hayCadencia);
		track.setAltitud(hayAltitud);
		track.setHr(hayHr);
		track.setPotencia(hayPotencia);
		track.setTipo(getTipo());
		return track;
	}
	
	public Document format (RawTrack track) throws Exception {
		Document document = getPlantilla();
		
		insertaSecciones(document, track);
		
		setNombre(document, track.getNombre());
		setAutor(document, track.getAutor());
		setDispositivo(document, track.getDispositivo());
		setTipoActividad(document, track.getTipoActividad());
		setDescripcion(document, track.getDescripcion());
		setCalorias(document, track.getCalorias());
		setTime(document, track.getTime());
		
		return document;
	}
	
	private Document getPlantilla() throws Exception  {
		DocumentBuilderFactory factory = getFactory();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();	
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(getNombrePlantilla());
		return documentBuilder.parse(inputStream);		
	}
	
}

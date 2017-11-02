package aplicaciones.gpsedit.gps;

import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import aplicaciones.gpsedit.beans.Autor;
import aplicaciones.gpsedit.beans.Dispositivo;
import aplicaciones.gpsedit.beans.RawSeccion;
import aplicaciones.gpsedit.beans.RawTrack;
import aplicaciones.gpsedit.beans.RawTrackPoint;
import aplicaciones.gpsedit.beans.TipoActividad;
import aplicaciones.gpsedit.util.UtilidadesMath;

public class GPSParserGPX extends GPSParser{
	
	public DocumentBuilderFactory getFactory() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
		return factory;
	}
	
	public NodeList getSecciones(Element node) {
		return node.getElementsByTagName("trkseg");
	}
	
	public NodeList getPuntos(Element node) {
		return node.getElementsByTagName("trkpt");
	}

	public double getLatitud(Element nodo) {
		return getDoubleAttr(nodo, "lat");
	}

	public double getLongitud(Element nodo) {
		return getDoubleAttr(nodo, "lon");
	}
	
	public double getAltitud(Element nodo) {
		return getDoubleNodo(nodo, "ele");
	}

	public long getHR(Element nodo) {
		return getLongNodoNS(nodo, "hr");
	}
	
	public long getCadencia(Element nodo) {
		return getLongNodoNS(nodo, "cad");
	}
	
	public Date getTiempo(Element nodo) {
		return getDateNodo(nodo, "time");
	}

	public double getVelocidad(Element nodo) {
		return 0f;
	}

	public long getPotencia(Element nodo) {
		return getLongNodo(nodo, "power");
	}
	
	public double getDistancia(Element nodo) {
		return 0f;
	}
	
	public String getTriggerMethod(Element nodo) {
		return null;
	}

	public String getIntensidad(Element nodo) {
		return null;
	}
	
	public Dispositivo getDispositivo(Element nodo)  {
		return null;
	}
	
	public TipoActividad getTipoActividad(Element nodo)  {
		TipoActividad tipo = null;
		try {
			String valor = ((Element)nodo.getElementsByTagName("trk").item(0)).getElementsByTagName("type").item(0).getFirstChild().getNodeValue();
			if ("RUNNING".equalsIgnoreCase(valor)) tipo = TipoActividad.RUNNING;
			if ("ROAD_BIKING".equalsIgnoreCase(valor)) tipo = TipoActividad.CICLISMO_CARRETERA;
			if ("MOUNTAIN_BIKING".equalsIgnoreCase(valor)) tipo = TipoActividad.CICLISMO_MTB;
			if ("SPINNING".equalsIgnoreCase(valor)) tipo = TipoActividad.CICLISMO_INTERIOR;
			if ("SKIING_DOWNHILL".equalsIgnoreCase(valor)) tipo = TipoActividad.ESQUI;
			if ("HIKING".equalsIgnoreCase(valor)) tipo = TipoActividad.SENDERISMO;
			if ("SWIMMING".equalsIgnoreCase(valor)) tipo = TipoActividad.NATACION;
		} catch (Exception e)  {}
		return tipo;
	}
	
	public Autor getAutor(Element nodo) {
		Autor autor = null;
		Element nodoAutor = (Element)nodo.getElementsByTagName("Author").item(0);
		if (nodoAutor != null)  {
			autor = new Autor();
			autor.setName(getStringNodo(nodoAutor, "name"));
		} else autor = Autor.GPSEdit;
		return autor;
	}

	public String getNombre(Element nodo) {
		String nombre = null;
		try {
			nombre = ((Element)nodo.getElementsByTagName("trk").item(0)).getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
		} catch (Exception e)  {}
		return nombre;
	}

	public String getDescripcion(Element nodo) {
		String desc = null;
		try {
			desc = ((Element)nodo.getElementsByTagName("trk").item(0)).getElementsByTagName("cmt").item(0).getFirstChild().getNodeValue();
		} catch (Exception e)  {}
		return desc;
	}

	public long getCalorias(Element nodo) {
		return 0;
	}

	public void setAutor(Document document, Autor valor) {
		if (valor != null)  {
			Node nodo = ((Element)document.getElementsByTagName("author").item(0)).getElementsByTagName("name").item(0);
			if (valor.getName() != null) nodo.appendChild(document.createTextNode(valor.getName()));
		} else {
			Node nodo = document.getElementsByTagName("author").item(0);
			nodo.getParentNode().removeChild(nodo);
		}
	}

	public void setNombre(Document document, String valor) {
		Node nodo = ((Element)document.getElementsByTagName("trk").item(0)).getElementsByTagName("name").item(0);
		if (valor != null)  {
			nodo.appendChild(document.createTextNode(valor));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}

	public void setTipoActividad(Document document, TipoActividad valor) {
		if (valor != null)  {
			Node nodo = ((Element)document.getElementsByTagName("trk").item(0)).getElementsByTagName("type").item(0);
			if (TipoActividad.RUNNING.equals(valor)) nodo.appendChild(document.createTextNode("RUNNING"));
			else if (TipoActividad.CICLISMO_CARRETERA.equals(valor)) nodo.appendChild(document.createTextNode("ROAD_BIKING"));
			else if (TipoActividad.CICLISMO_MTB.equals(valor)) nodo.appendChild(document.createTextNode("MOUNTAIN_BIKING"));
			else if (TipoActividad.CICLISMO_INTERIOR.equals(valor)) nodo.appendChild(document.createTextNode("SPINNING"));
			else if (TipoActividad.ESQUI.equals(valor)) nodo.appendChild(document.createTextNode("SKIING_DOWNHILL"));
			else if (TipoActividad.SENDERISMO.equals(valor)) nodo.appendChild(document.createTextNode("HIKING"));
			else if (TipoActividad.NATACION.equals(valor)) nodo.appendChild(document.createTextNode("SWIMMING"));
			else nodo.appendChild(document.createTextNode("OTHER"));
		}
	}

	public void setDispositivo(Document document, Dispositivo valor) {
		//No implementado en el formato GPX
	}

	public void setDescripcion(Document document, String valor) {
		Node nodo = ((Element)document.getElementsByTagName("trk").item(0)).getElementsByTagName("cmt").item(0);
		if(valor != null)  {
			nodo.appendChild(document.createTextNode(valor));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}

	public void setCalorias(Document document, long valor) {
		//No implementado en el formato GPX
	}
	
	public void setTime(Document document, Date valor) {
		if(valor != null)  {
			Node nodo = ((Element)document.getElementsByTagName("metadata").item(0)).getElementsByTagName("time").item(0);
			nodo.appendChild(document.createTextNode(df1.format(valor)));
		}
	}

	public String getNombrePlantilla() {
		return GPSParser.PLANTILLA_GPX;
	}
	
	public String getTipo() {
		return GPSParser.GPX;
	}
	
	public void insertaSecciones(Document document, RawTrack track) {

		Element nodoSeccion = (Element)document.getElementsByTagName("trkseg").item(0);
		Element nodoPuntoPlantilla = (Element)nodoSeccion.getElementsByTagName("trkpt").item(0);
		nodoSeccion.removeChild(nodoPuntoPlantilla);
		
		for (RawSeccion seccion:track.getSecciones())  {

			for (RawTrackPoint punto:seccion.getPuntos())  {
				Element nodoPunto = (Element) nodoPuntoPlantilla.cloneNode(true);
				nodoSeccion.appendChild(nodoPunto);

				setNodeAtribute(document, nodoPunto, "lat", (punto.getLatitud() != 0)?punto.getLatitud():null);
				setNodeAtribute(document, nodoPunto, "lon", (punto.getLongitud() != 0)?punto.getLongitud():null);

				
				setNodeValue(document, nodoPunto, "time", df2.format(punto.getTiempo()));
				setNodeValue(document, nodoPunto, "ele", (track.isHayGPS())?UtilidadesMath.round(punto.getAltitud(), 1):null);
//				setNodeValue(document, nodoPunto, "DistanceMeters", UtilidadesMath.round(punto.getDistancia(), 1));
				setNodeValue(document, nodoPunto, "gpxtpx:hr", (punto.getHr() != 0)?punto.getHr():null);
				setNodeValue(document, nodoPunto, "gpxtpx:cad", (track.isHayCadencia())?punto.getCadencia():null);
				setNodeValue(document, nodoPunto, "power", (punto.getPotencia() != 0)?punto.getPotencia():null);

				

				
			}
		}

		
	}
	
}

package aplicaciones.gpsedit.gps;

import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
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

public class GPSParserTCX extends GPSParser{
	
	public DocumentBuilderFactory getFactory() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(false);
		return factory;
	}
	
	public NodeList getSecciones(Element node) {
		return node.getElementsByTagName("Lap");
	}
	
	public NodeList getPuntos(Element node) {
		return node.getElementsByTagName("Trackpoint");
	}
	
	public double getLatitud(Element nodo) {
		return getDoubleNodo(nodo, "LatitudeDegrees");
	}

	public double getLongitud(Element nodo) {
		return getDoubleNodo(nodo, "LongitudeDegrees");
	}
	
	public double getAltitud(Element nodo) {
		return getDoubleNodo(nodo, "AltitudeMeters");
	}

	public long getHR(Element nodo) {
		return getLongNodo(nodo, "Value");
	}
	
	public long getCadencia(Element nodo) {
		return getLongNodo(nodo, "Cadence");
	}
	
	public double getDistancia(Element nodo) {
		return getDoubleNodo(nodo, "DistanceMeters");
	}
	
	public Date getTiempo(Element nodo) throws DOMException, ParseException {
		return getDateNodo(nodo, "Time");
	}
	
	public double getVelocidad(Element nodo) {
		return getDoubleNodo(nodo, "Speed");
	}

	public long getPotencia(Element nodo) {
		return getLongNodo(nodo, "Watts");
	}

	
	public String getTriggerMethod(Element nodo) {
		return getStringNodo(nodo, "TriggerMethod");
	}

	public String getIntensidad(Element nodo) {
		return getStringNodo(nodo, "Intensity");
	}

	public Dispositivo getDispositivo(Element nodo)  {
		Dispositivo dispositivo = null;
		Element nodoCreador = (Element)nodo.getElementsByTagName("Creator").item(0);
		if (nodoCreador != null)  {
			dispositivo = new Dispositivo();
			dispositivo.setName(getStringNodo(nodoCreador, "Name"));
			dispositivo.setProductID(getLongNodo(nodoCreador, "ProductID"));
			dispositivo.setUnitId(getLongNodo(nodoCreador, "UnitId"));
			dispositivo.setVersionMajor(getLongNodo(nodoCreador, "VersionMajor"));
			dispositivo.setVersionMinor(getLongNodo(nodoCreador, "VersionMinor"));
			dispositivo.setBuildMajor(getLongNodo(nodoCreador, "BuildMajor"));
			dispositivo.setBuildMinor(getLongNodo(nodoCreador, "BuildMinor"));
		}
		return dispositivo;		
	}
	
	public TipoActividad getTipoActividad(Element nodo)  {
		TipoActividad tipo = null;
		try {
			String valor = ((Element)nodo.getElementsByTagName("Activity").item(0)).getAttribute("Sport");
			if ("RUNNING".equalsIgnoreCase(valor)) tipo = TipoActividad.RUNNING;
		} catch (Exception e)  {}
		return tipo;		
	}

	public Autor getAutor(Element nodo) {
		Autor autor = null;
		Element nodoAutor = (Element)nodo.getElementsByTagName("Author").item(0);
		if (nodoAutor != null)  {
			autor = new Autor();
			autor.setName(getStringNodo(nodoAutor, "Name"));
			autor.setVersionMajor(getLongNodo(nodoAutor, "VersionMajor"));
			autor.setVersionMinor(getLongNodo(nodoAutor, "VersionMinor"));
			autor.setBuildMajor(getLongNodo(nodoAutor, "BuildMajor"));
			autor.setBuildMinor(getLongNodo(nodoAutor, "BuildMinor"));
			autor.setLangID(getStringNodo(nodoAutor, "LangID"));
			autor.setPartNumber(getStringNodo(nodoAutor, "PartNumber"));
		} else autor = Autor.GPSEdit;
		return autor;	
	}

	public String getNombre(Element nodo) {
		//no implementado en TCX
		return null;
	}

	public String getDescripcion(Element nodo) {
		String desc = null;
		try {
			desc = ((Element)nodo.getElementsByTagName("Activity").item(0)).getElementsByTagName("Notes").item(0).getFirstChild().getNodeValue();
		} catch (Exception e)  {}
		return desc;		
	}

	public long getCalorias(Element nodo) {
		return getLongNodo(nodo, "Calories");
	}
	
	public void setAutor(Document document, Autor valor) {
		Element nodo = (Element)document.getElementsByTagName("Author").item(0);
		if (valor != null)  {
			if (valor.getName() != null) nodo.getElementsByTagName("Name").item(0).appendChild(document.createTextNode(valor.getName()));
			if (valor.getVersionMajor() != null) nodo.getElementsByTagName("VersionMajor").item(0).appendChild(document.createTextNode(valor.getVersionMajor().toString()));
			if (valor.getVersionMinor() != null) nodo.getElementsByTagName("VersionMinor").item(0).appendChild(document.createTextNode(valor.getVersionMinor().toString()));
			if (valor.getBuildMajor() != null) nodo.getElementsByTagName("BuildMajor").item(0).appendChild(document.createTextNode(valor.getBuildMajor().toString()));
			if (valor.getBuildMinor() != null) nodo.getElementsByTagName("BuildMinor").item(0).appendChild(document.createTextNode(valor.getBuildMinor().toString()));
			if (valor.getLangID() != null) nodo.getElementsByTagName("LangID").item(0).appendChild(document.createTextNode(valor.getLangID()));
			if (valor.getPartNumber() != null) nodo.getElementsByTagName("PartNumber").item(0).appendChild(document.createTextNode(valor.getPartNumber()));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}

	public void setNombre(Document document, String valor) {
		//no implementado en TCX
	}

	public void setTipoActividad(Document document, TipoActividad valor) {
		if(valor != null)  {
			Element nodo = (Element)document.getElementsByTagName("Activity").item(0);
			if (TipoActividad.RUNNING.equals(valor)) nodo.setAttribute("Sport", "Running");
			else if (TipoActividad.CICLISMO_CARRETERA.equals(valor)) nodo.setAttribute("Sport", "Biking");
			else if (TipoActividad.CICLISMO_MTB.equals(valor)) nodo.setAttribute("Sport", "Biking");
			else if (TipoActividad.CICLISMO_INTERIOR.equals(valor)) nodo.setAttribute("Sport", "Biking");
			else nodo.setAttribute("Sport", "Other");
		}
	}

	public void setDispositivo(Document document, Dispositivo valor) {
		Element nodo = (Element)document.getElementsByTagName("Creator").item(0);
		if (valor != null)  {
			if (valor.getName() != null) nodo.getElementsByTagName("Name").item(0).appendChild(document.createTextNode(valor.getName()));
			if (valor.getProductID() != null) nodo.getElementsByTagName("ProductID").item(0).appendChild(document.createTextNode(valor.getProductID().toString()));
			if (valor.getUnitId() != null) nodo.getElementsByTagName("UnitId").item(0).appendChild(document.createTextNode(valor.getUnitId().toString()));
			if (valor.getVersionMajor() != null) nodo.getElementsByTagName("VersionMajor").item(0).appendChild(document.createTextNode(valor.getVersionMajor().toString()));
			if (valor.getVersionMinor() != null) nodo.getElementsByTagName("VersionMinor").item(0).appendChild(document.createTextNode(valor.getVersionMinor().toString()));
			if (valor.getBuildMajor() != null) nodo.getElementsByTagName("BuildMajor").item(0).appendChild(document.createTextNode(valor.getBuildMajor().toString()));
			if (valor.getBuildMinor() != null) nodo.getElementsByTagName("BuildMinor").item(0).appendChild(document.createTextNode(valor.getBuildMinor().toString()));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}

	public void setDescripcion(Document document, String valor) {
		Node nodo = ((Element)document.getElementsByTagName("Activity").item(0)).getElementsByTagName("Notes").item(0);
		if(valor != null)  {
			nodo.appendChild(document.createTextNode(valor));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}

	public void setCalorias(Document document, long valor) {
		Node nodo = ((Element)document.getElementsByTagName("Activity").item(0)).getElementsByTagName("Calories").item(0);
		if(valor != 0)  {
			nodo.appendChild(document.createTextNode(String.valueOf(valor)));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}
	
	public void setTime(Document document, Date valor) {
		Node nodo = ((Element)document.getElementsByTagName("Activity").item(0)).getElementsByTagName("Id").item(0);
		if(valor != null)  {
			nodo.appendChild(document.createTextNode(df1.format(valor)));
		} else {
			nodo.getParentNode().removeChild(nodo);
		}
	}



	
	public String getNombrePlantilla() {
		return GPSParser.PLANTILLA_TCX;
	}
	
	public String getTipo() {
		return GPSParser.TCX;
	}


	public void insertaSecciones(Document document, RawTrack track) {
		
		Element nodoSeccionPlantilla = (Element)document.getElementsByTagName("Lap").item(0);
		Node padreSecciones = nodoSeccionPlantilla.getParentNode();
		nodoSeccionPlantilla.getParentNode().removeChild(nodoSeccionPlantilla);
		

		
		for (RawSeccion seccion:track.getSecciones())  {
			Element nodoSeccion = (Element) nodoSeccionPlantilla.cloneNode(true);
			padreSecciones.appendChild(nodoSeccion);
			
			setNodeValue(document, nodoSeccion, "TotalTimeSeconds", Math.round(seccion.getTiempoTotal() / 1000));
			setNodeValue(document, nodoSeccion, "DistanceMeters", UtilidadesMath.round(seccion.getLongitud(), 1));
			setNodeValue(document, nodoSeccion, "MaximumSpeed",UtilidadesMath.round(seccion.getVelocidadMaxima() / 3.6, 3)); 
			setNodeValue(document, nodoSeccion, "AvgSpeed", UtilidadesMath.round(seccion.getVelocidadMed() / 3.6, 3)); 
			setNodeValue(document, nodoSeccion, "AverageHeartRateBpm", seccion.getHRMed());
			setNodeValue(document, nodoSeccion, "MaximumHeartRateBpm", seccion.getHRMax());
			setNodeValue(document, nodoSeccion, "Intensity", seccion.getIntensidad());
			setNodeValue(document, nodoSeccion, "Cadence", seccion.getCadenciaMed());
			setNodeValue(document, nodoSeccion, "TriggerMethod", seccion.getTriggerMethod());

			Element nodoPuntoPlantilla = (Element)nodoSeccion.getElementsByTagName("Trackpoint").item(0);
			Node padrePuntos = nodoPuntoPlantilla.getParentNode();
			nodoPuntoPlantilla.getParentNode().removeChild(nodoPuntoPlantilla);

			for (RawTrackPoint punto:seccion.getPuntos())  {
				Element nodoPunto = (Element) nodoPuntoPlantilla.cloneNode(true);
				padrePuntos.appendChild(nodoPunto);
				
				setNodeValue(document, nodoPunto, "Time", df2.format(punto.getTiempo()));
				setNodeValue(document, nodoPunto, "LatitudeDegrees", (punto.getLatitud() != 0)?punto.getLatitud():null);
				setNodeValue(document, nodoPunto, "LongitudeDegrees", (punto.getLongitud() != 0)?punto.getLongitud():null);
				setNodeValue(document, nodoPunto, "AltitudeMeters", (track.isAltitud())?UtilidadesMath.round(punto.getAltitud(), 1):null);
				setNodeValue(document, nodoPunto, "DistanceMeters", UtilidadesMath.round(punto.getDistancia(), 1));
				setNodeValue(document, nodoPunto, "Value", (punto.getHr() != 0)?punto.getHr():null);
				setNodeValue(document, nodoPunto, "Cadence", (track.isCadencia())?punto.getCadencia():null);
				setNodeValue(document, nodoPunto, "Speed", UtilidadesMath.round(punto.getVelocidad() / 3.6, 3));
				setNodeValue(document, nodoPunto, "Watts", (punto.getPotencia() != 0)?punto.getPotencia():null);
			}
		}
	}
}

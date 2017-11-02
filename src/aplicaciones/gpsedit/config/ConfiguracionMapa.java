package aplicaciones.gpsedit.config;

import java.awt.Color;

import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo.MVEMode;


public class ConfiguracionMapa {
	private boolean mostrarPuntos = false;
	private Color colorRuta = Color.RED;
	private Color colorSegmento = Color.BLUE;
	private boolean mover = true;
	private boolean osm = true;
	private MVEMode modoMapa = VirtualEarthTileFactoryInfo.MAP;
	
	public MVEMode getModoMapa() {
		return modoMapa;
	}
	public boolean isMostrarPuntos() {
		return mostrarPuntos;
	}
	public void setModoMapa(MVEMode modoMapa) {
		this.modoMapa = modoMapa;
	}
	public void setMostrarPuntos(boolean mostrarPuntos) {
		this.mostrarPuntos = mostrarPuntos;
	}
	public Color getColorRuta() {
		return colorRuta;
	}
	public Color getColorSegmento() {
		return colorSegmento;
	}
	public void setColorRuta(Color colorRuta) {
		this.colorRuta = colorRuta;
	}
	public void setColorSegmento(Color colorSegmento) {
		this.colorSegmento = colorSegmento;
	}
	public boolean isSeleccionar() {
		return !mover;
	}
	public boolean isMover() {
		return mover;
	}
	public boolean isOsm() {
		return osm;
	}
	public void setMover(boolean mover) {
		this.mover = mover;
	}
	public void setOsm(boolean osm) {
		this.osm = osm;
	}
}

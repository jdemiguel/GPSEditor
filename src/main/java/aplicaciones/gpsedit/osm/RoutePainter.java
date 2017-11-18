package aplicaciones.gpsedit.osm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionMapa;

public class RoutePainter implements Painter<JXMapViewer> {

	private boolean antiAlias = true;
	
	private List<GeoPosition> trackRuta;
	private List<GeoPosition> trackSegmento;
	
	private ConfiguracionMapa configuracion;
	
	/**
	 * @param track the track
	 */
	public RoutePainter(List<GeoPosition> trackRuta, List<GeoPosition> trackSegmento) {
		this.trackRuta = trackRuta;
		this.trackSegmento = trackSegmento;
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionMapa();

	}

	public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
		g = (Graphics2D) g.create();

		// convert from viewport to world bitmap
		Rectangle rect = map.getViewportBounds();
		g.translate(-rect.x, -rect.y);

		if (antiAlias)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		
		if (trackRuta != null) {
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(6));
			pintaRuta(g, map, trackRuta, false);
	
			g.setColor(configuracion.getColorRuta());
			g.setStroke(new BasicStroke(2));
			pintaRuta(g, map, trackRuta, false);
		}
		if (trackSegmento != null) {
	
			g.setColor(configuracion.getColorSegmento());
			g.setStroke(new BasicStroke(2));
			pintaRuta(g, map, trackSegmento, configuracion.isMostrarPuntos());
		}
		

		g.dispose();
	}

	/**
	 * @param g the graphics object
	 * @param map the map
	 */
	private void pintaRuta(Graphics2D g, JXMapViewer map, List<GeoPosition> track, boolean showPuntos) {
		int xAnterior = 0;
		int yAnterior = 0;
		
		for (int i = 0; i< track.size(); i++ ) {
			GeoPosition gp = track.get(i);
			Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
			
			// convert geo-coordinate to world bitmap pixel
			int x = (int) pt.getX();
			int y = (int) pt.getY();

			if (i > 0) {
				g.drawLine(xAnterior, yAnterior, x, y);
			}
			
			if (showPuntos && map.getZoom() == 2) g.fillOval(x - 2, y - 2, 5, 5);
			if (showPuntos && map.getZoom() == 1) g.fillOval(x - 3, y - 3, 7, 7);
			xAnterior = x;
			yAnterior = y;
		}
	}

}
package aplicaciones.gpsedit.actividad;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.LocalResponseCache;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionMapa;
import aplicaciones.gpsedit.listeners.ResizeDinamicoListener;
import aplicaciones.gpsedit.osm.RoutePainter;
import aplicaciones.gpsedit.osm.SelectionAdapter;
import aplicaciones.gpsedit.osm.SelectionPainter;
import aplicaciones.gpsedit.util.UtilidadesSwing;


public class PanelMapaActividad extends JLayeredPane implements ActionListener {

	private static final long serialVersionUID = -2453350388373410133L;
	
	private ActionListener listener;
	private DatosActividad datosActividad;

	//private JXMapKit mapKit;
	private ArrayList<GeoPosition> trackRuta;
	private ArrayList<GeoPosition> trackSegmento;

	private JXMapKit mapKit;
	private ConfiguracionMapa configuracion;

	private SelectionAdapter seleccionListener;
	private MouseInputListener moverListener;
	
	public PanelMapaActividad(ActionListener listener) {
		this.listener = listener;
		setLayout(null);
		setBounds(0,0,1300,768);
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionMapa();
		//setBackground();
		
//		add(panelMapa, JLayeredPane.DEFAULT_LAYER);
		
		JPanel toolbar = new JPanel();
		toolbar.setBackground(Color.GRAY);
		toolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		toolbar.setBounds(10, 10, 64, 128);
		toolbar.setLayout(new GridLayout(4,2));
		
		JToggleButton botonMove = new JToggleButton(UtilidadesSwing.getIcono("resources/icons8-Move-64.png", 24, 24));
		botonMove.setSelected(configuracion.isMover());
		botonMove.setActionCommand(ConstantesAcciones.MOVE);
		botonMove.addActionListener(this);
		toolbar.add(botonMove);
		JToggleButton botonSelect = new JToggleButton(UtilidadesSwing.getIcono("resources/icons8-Select None-64.png", 24, 24));
		botonSelect.setSelected(!configuracion.isMover());
		botonSelect.setActionCommand(ConstantesAcciones.SELECT);
		botonSelect.addActionListener(this);
		toolbar.add(botonSelect);
		JButton botonZoomIn = new JButton(UtilidadesSwing.getIcono("resources/icons8-Zoom In-50.png", 24, 24));
		botonZoomIn.setActionCommand(ConstantesAcciones.ZOOM_IN);
		botonZoomIn.addActionListener(this);
		toolbar.add(botonZoomIn);
		JButton botonZoomOut = new JButton(UtilidadesSwing.getIcono("resources/icons8-Zoom Out-50.png", 24, 24));
		botonZoomOut.setActionCommand(ConstantesAcciones.ZOOM_OUT);
		botonZoomOut.addActionListener(this);
		toolbar.add(botonZoomOut);
		JButton botonZoomFit = new JButton(UtilidadesSwing.getIcono("resources/icons8-Zoom to Extents-50.png", 24, 24));
		botonZoomFit.setActionCommand(ConstantesAcciones.ZOOM_FIT);
		botonZoomFit.addActionListener(this);
		toolbar.add(botonZoomFit);
		JToggleButton botonEdit = new JToggleButton(UtilidadesSwing.getIcono("resources/icons8-Edit-50.png", 24, 24));
		botonEdit.setActionCommand(ConstantesAcciones.EDIT);
		botonEdit.addActionListener(this);
		toolbar.add(botonEdit);
		JButton botonConfig = new JButton(UtilidadesSwing.getIcono("resources/icons8-Settings-50.png", 24, 24));
		botonConfig.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_MAPA);
		botonConfig.addActionListener(this);
		toolbar.add(botonConfig);
		JButton botonDelete = new JButton(UtilidadesSwing.getIcono("resources/icons8-Delete Bin-50.png", 24, 24));
		botonDelete.setActionCommand(ConstantesAcciones.DELETE_ALL);
		botonDelete.addActionListener(listener);
		toolbar.add(botonDelete);
		add(toolbar, JLayeredPane.PALETTE_LAYER);
		ButtonGroup variableMoveSelect = new ButtonGroup();
		variableMoveSelect.add(botonSelect);
		variableMoveSelect.add(botonMove);

		mapKit = new JXMapKit();
		mapKit.getMainMap().setBounds(0,0,1300,768);
		mapKit.getMainMap().addHierarchyBoundsListener(new ResizeDinamicoListener());

		mapKit.setZoom(7);
		mapKit.setAddressLocation(new GeoPosition(40.681, -3.771));

		add(mapKit.getMainMap(), JLayeredPane.DEFAULT_LAYER);


		//eliminamos los listeners
		
		borrarMouseListeners();
		mapKit.getMainMap().addKeyListener(new PanKeyListener(mapKit.getMainMap()));

		// Add a selection painter
		seleccionListener = new SelectionAdapter(mapKit.getMainMap(), this);
		moverListener = new PanMouseInputListener(mapKit.getMainMap());
		
		
		trackRuta = new ArrayList<GeoPosition>();
		trackSegmento = new ArrayList<GeoPosition>();
		
		RoutePainter routePainter = new RoutePainter(trackRuta, trackSegmento);
		SelectionPainter sp = new SelectionPainter(seleccionListener); 
		
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(routePainter);
		painters.add(sp);
		
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		mapKit.getMainMap().setOverlayPainter(painter);
		
		}

	public void update(DatosActividad datosActividad)  {
		this.datosActividad = datosActividad;
		update();
	}
	public void update()  {
		setMouseListeners();
		List<TrackPoint> puntos = datosActividad.getTrack().getPuntos();
		trackRuta.clear();
		trackSegmento.clear();
		for (int i = 0; i< puntos.size(); i++)  {
			TrackPoint punto = puntos.get(i);
			//los puntos sin info GPS no se muestran en el mapa
			if (punto.getLatitud() == 0 || punto.getLongitud() == 0) continue;
			GeoPosition gp = new GeoPosition(punto.getLatitud(), punto.getLongitud());
			trackRuta.add(gp);
			if (i>=datosActividad.getInicioRango() && i<=datosActividad.getFinRango()) trackSegmento.add(gp);
		}

		TileFactoryInfo info = null;
		if (configuracion.isOsm()) info = new OSMTileFactoryInfo();
		else info = new VirtualEarthTileFactoryInfo(configuracion.getModoMapa());
		File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		LocalResponseCache.installResponseCache(info.getBaseURL(), cacheDir, false);
			
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(8);
		mapKit.setTileFactory(tileFactory);
		zoomFit();
	}
	
	public void updateConfiguracion()  {
		update();
	}
	
	private void borrarMouseListeners()  {
		MouseListener[] mouseListeners = mapKit.getMainMap().getMouseListeners();
		for (MouseListener mouseListener:mouseListeners) mapKit.getMainMap().removeMouseListener(mouseListener);
		MouseMotionListener[] mouseMotionListeners = mapKit.getMainMap().getMouseMotionListeners();
		for (MouseMotionListener mouseMotionListener:mouseMotionListeners) mapKit.getMainMap().removeMouseMotionListener(mouseMotionListener);
	}
	
	private void setMouseListeners()  {
		borrarMouseListeners();
		if (configuracion.isSeleccionar())  {
			mapKit.getMainMap().addMouseListener(seleccionListener); 
			mapKit.getMainMap().addMouseMotionListener(seleccionListener);
		} else {
			mapKit.getMainMap().addMouseListener(moverListener); 
			mapKit.getMainMap().addMouseMotionListener(moverListener);
		}
		
	}
	
	private void zoomFit()  {
		HashSet<GeoPosition> posiciones = new HashSet<GeoPosition>(trackSegmento);
		mapKit.getMainMap().zoomToBestFit(posiciones, 0.9);
	}
	
	public void selectRectangulo (Rectangle r)  {
		TileFactory tileFactory = mapKit.getMainMap().getTileFactory();
		List<TrackPoint> puntos = datosActividad.getTrack().getPuntos();
		int zoom = mapKit.getMainMap().getZoom();
		Rectangle rect = mapKit.getMainMap().getViewportBounds();
		double x0 = rect.getMinX();
		double y0 = rect.getMinY();
		int inicioRango = -1;
		int finRango = -1;
		for (int index = 0; index < puntos.size(); index++)  {
			TrackPoint trackPoint = puntos.get(index);
			Point2D punto = tileFactory.geoToPixel(new GeoPosition(trackPoint.getLatitud(), trackPoint.getLongitud()), zoom);
			//lo llevamos del bitmap mundial al visible
			punto.setLocation(punto.getX() - x0, punto.getY() - y0);
			if (inicioRango < 0 && r.contains(punto)) {
				inicioRango = index;
			}
			if (r.contains(punto))  {
				finRango = index;
			}
		}
		if (inicioRango >= 0 && finRango > inicioRango) {
			try {
				datosActividad.setRango(inicioRango, finRango);
				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.ACTION_SELECT_RECTANGULO.equalsIgnoreCase(evento.getActionCommand()))  {
			SelectionAdapter source = (SelectionAdapter) evento.getSource();
			selectRectangulo(source.getRectangle());
		}
		if (ConstantesAcciones.EDIT.equalsIgnoreCase(evento.getActionCommand()))  {
		}
		if (ConstantesAcciones.ZOOM_IN.equalsIgnoreCase(evento.getActionCommand()))  {
			mapKit.getMainMap().setZoom(mapKit.getMainMap().getZoom() - 1);
		}
		if (ConstantesAcciones.ZOOM_OUT.equalsIgnoreCase(evento.getActionCommand()))  {
			mapKit.getMainMap().setZoom(mapKit.getMainMap().getZoom() + 1);
		}
		if (ConstantesAcciones.ZOOM_FIT.equalsIgnoreCase(evento.getActionCommand()))  {
			zoomFit();
		}
		if (ConstantesAcciones.SELECT.equalsIgnoreCase(evento.getActionCommand()))  {
			configuracion.setMover(false);
			setMouseListeners();
		}
		if (ConstantesAcciones.MOVE.equalsIgnoreCase(evento.getActionCommand()))  {
			configuracion.setMover(true);
			setMouseListeners();
		}
		if (ConstantesAcciones.CAMBIAR_CONFIGURACION_MAPA.equalsIgnoreCase(evento.getActionCommand()))  {
			listener.actionPerformed(evento);
		}
		if (ConstantesAcciones.RESET_RANGO.equalsIgnoreCase(evento.getActionCommand()))  {
			datosActividad.resetRango();
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}
	}
	
}

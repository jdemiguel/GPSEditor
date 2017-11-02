package aplicaciones.gpsedit.actividad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYBoxAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.beans.DatosGraficaBean;
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.SegmentoCoeficiente;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionCoeficientes;
import aplicaciones.gpsedit.listeners.PopupGraficoAdapter;
import aplicaciones.gpsedit.listeners.ResizeDinamicoListener;
import aplicaciones.gpsedit.util.EjeX;
import aplicaciones.gpsedit.util.UtilidadesFormat;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelCoeficientes extends JLayeredPane implements ChartMouseListener, AxisChangeListener, MouseListener, ActionListener {
	private static final long serialVersionUID = -7308323246155546299L;

	private JFreeChart grafico;
	private ChartPanel panelGrafico;
	private XYPlot plot;
	private XYTextAnnotation annotationAlt;
	private XYTextAnnotation annotationPte;
	private XYLineAnnotation annotationLine;
	XYSeries serie;

	private DatosActividad datosActividad;
	private DatosGraficaBean datosGrafica;
	private ConfiguracionCoeficientes configuracion;
	private PopupGraficoAdapter popupGraficoAdapter;
	private ActionListener listener;
	
	public PanelCoeficientes(ActionListener listener)  {
		this.listener = listener;
		setLayout(null);
		setBounds(0, 0, 1300, 560);
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionCoeficientes();
		
		annotationAlt = new XYTextAnnotation("", 0, 0);
		annotationAlt.setBackgroundPaint(Color.WHITE);
		annotationAlt.setOutlineVisible(true);
		annotationAlt.setOutlineStroke(new BasicStroke(.5f));
		annotationPte = new XYTextAnnotation("", 0, 0);
		annotationPte.setBackgroundPaint(Color.WHITE);
		annotationPte.setOutlineVisible(true);
		annotationPte.setOutlineStroke(new BasicStroke(.5f));
		annotationLine = new XYLineAnnotation(0, 0, 0, 0);
		plot = new XYPlot();
		plot.setDomainCrosshairVisible(false);
		NumberAxis rangeAxis = new NumberAxis("Altitud (m)");
		rangeAxis.setAutoRangeIncludesZero(false);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRange(true);
		rangeAxis.setUpperMargin(.15);
		rangeAxis.setLowerMargin(.15);
		plot.setRangeAxis(rangeAxis);
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

		NumberAxis domainAxis = new NumberAxis("Distancia (km)");
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		domainAxis.setUpperMargin(.15);
		domainAxis.setLowerMargin(.15);
		
		plot.setDomainAxis(domainAxis);
		grafico = new JFreeChart("Gráfica",JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		grafico.setBackgroundPaint(Color.WHITE);
        

		panelGrafico = new ChartPanel(grafico);
		panelGrafico.addMouseListener(this);
		panelGrafico.setDomainZoomable(true);
		panelGrafico.setRangeZoomable(false);
		panelGrafico.addChartMouseListener(this);
		
		panelGrafico.setBounds(0, 0, 1300, 50);
		panelGrafico.addHierarchyBoundsListener(new ResizeDinamicoListener());

		JPopupMenu menu = panelGrafico.getPopupMenu();
		menu.removeAll();
		
		popupGraficoAdapter = new PopupGraficoAdapter(listener);
		menu.addPopupMenuListener(popupGraficoAdapter);
		
		JMenuItem jmenuItem = new JMenuItem("Copiar");
		jmenuItem.setActionCommand(ConstantesAcciones.COPY);
		jmenuItem.addActionListener(panelGrafico);
		menu.add(jmenuItem);
		jmenuItem = new JMenuItem("Guardar como...");
		jmenuItem.setActionCommand(ConstantesAcciones.SAVE);
		jmenuItem.addActionListener(panelGrafico);
		menu.add(jmenuItem);
		
		menu.add(new Separator());
		jmenuItem = new JMenuItem("Imprimir");
		jmenuItem.setActionCommand(ConstantesAcciones.PRINT);
		jmenuItem.addActionListener(panelGrafico);
		menu.add(jmenuItem);
		menu.add(new Separator());
		jmenuItem = new JMenuItem("Configuración");
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_COEFICIENTES);
		jmenuItem.addActionListener(listener);
		menu.add(jmenuItem);
		menu.add(new Separator());
		jmenuItem = new JMenuItem("Establecer como inicio");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_INICIO_RANGO);
		jmenuItem.addActionListener(popupGraficoAdapter);
		menu.add(jmenuItem);
		jmenuItem = new JMenuItem("Establecer como fin");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_FIN_RANGO);
		jmenuItem.addActionListener(popupGraficoAdapter);
		menu.add(jmenuItem);
		jmenuItem = new JMenuItem("Cambiar título del gráfico");
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIO_TITULO);
		jmenuItem.addActionListener(this);
		menu.add(jmenuItem);		
		add(panelGrafico, JLayeredPane.DEFAULT_LAYER);
	}
	
	public void update(DatosActividad datosActividad)  {
		this.datosActividad = datosActividad;
		panelGrafico.setVisible(true);
		datosGrafica = datosActividad.getDatosGrafica(EjeX.getInstanciaEjeDistancia()).getDatosAltitud();
		popupGraficoAdapter.setDatosActividad(datosActividad);
		if (datosActividad.getTrack().getNombre() != null) grafico.getTitle().setText(datosActividad.getTrack().getNombre());
		else grafico.getTitle().setText("");
		update();
	}
	
	@SuppressWarnings("unchecked")
	public void update()  {
		EjeX ejeX = EjeX.getInstanciaEjeDistancia();
		ValueAxis eje = plot.getDomainAxis();
		double valorInicial = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(datosActividad.getInicioRango()), ejeX);
		double valorFinal = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(datosActividad.getFinRango()), ejeX);

		eje.removeChangeListener(this);
		double margen = ((double)configuracion.getIntervalo() - 1.0) / 1000.0;
		eje.setLowerBound( -margen);
		eje.setUpperBound(valorFinal - valorInicial + margen);

		
		UnivariateFunction datos = null;
		annotationAlt.setOutlinePaint(configuracion.getColorLinea());
		annotationPte.setOutlinePaint(configuracion.getColorLinea());
		if (configuracion.isSuavizado()) {
			datos = datosGrafica.getDatosSuavizados(configuracion.getAjusteSuavidad());
		} else {
			datos = datosGrafica.getDatosBrutos();
		}
		serie = new XYSeries("Altitud");

		for (int i=datosActividad.getInicioRango(); i<= datosActividad.getFinRango(); i++)  {
			double valorX = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(i), ejeX);
			try  {
				serie.add(valorX - valorInicial, datos.value(valorX));
			} catch (Exception e)  {
				//se descarta el punto. Probablemente sea un punto sin info de altura situado al principio 
			}
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);
		plot.setDataset(dataset);
		
		XYAreaRenderer renderer = new XYAreaRenderer(getTipo());
		renderer.setSeriesPaint(0, configuracion.getColorRelleno());
		renderer.setSeriesOutlinePaint(0, configuracion.getColorLinea());
		renderer.setSeriesOutlineStroke(0, new BasicStroke (.5f));
		
		renderer.setOutline(true);
		plot.setRenderer(renderer);

		if (plot.getRangeAxis() instanceof NumberAxis) {
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setAutoRangeIncludesZero(configuracion.isEjeCero());
			rangeAxis.setAutoRange(true);
		}
		
		eje.addChangeListener(this);
		
		List<XYAnnotation> lineas = plot.getAnnotations();
		for (XYAnnotation annotation:lineas) plot.removeAnnotation(annotation);
		
		List<SegmentoCoeficiente> segmentos = datosActividad.getSegmentosCoeficienteAPMRango();
		double alturaTexto = (plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() * 19) / 20;
		if (segmentos.size() < 50 ) {
			int segmentoMinimo = 0;
			for (int index  = 0; index < segmentos.size(); index++)  {
				SegmentoCoeficiente segmento = segmentos.get(index);
				if (segmento.getAlturaInicial() < segmentos.get(segmentoMinimo).getAlturaInicial()) segmentoMinimo = index;
				double valorX = (double)(configuracion.getIntervalo() * index ) / 1000.0;
				if (index > 0) {
					Stroke stroke = new BasicStroke(.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
					if (Math.round(valorX) != valorX) stroke = UtilidadesSwing.getStrokeDiscontinuo();
					XYLineAnnotation annotationLine = new XYLineAnnotation(valorX, plot.getRangeAxis().getLowerBound(), valorX, segmento.getAlturaInicial(), stroke, Color.BLACK);
					if (Math.round(valorX) != valorX && configuracion.isMostrarLineasSecundarias()) plot.addAnnotation(annotationLine, false);
					else if (Math.round(valorX) == valorX && configuracion.isMostrarLineasPrincipales()) plot.addAnnotation(annotationLine, false);
				}
				double valorIntermedio = (double)(configuracion.getIntervalo() * ((double)index + .5) ) / 1000.0;
				if (configuracion.isMostrarPendiente()) {
					XYTextAnnotation annotationText = new XYTextAnnotation(" " + UtilidadesFormat.getPercentFormat().format(segmento.getPendiente()) + " ", valorIntermedio, alturaTexto);
					annotationText.setTextAnchor(TextAnchor.TOP_CENTER);
					annotationText.setFont(annotationText.getFont().deriveFont(Font.PLAIN, 9f));
					if (index == segmentos.size() - 1)  {
						if (segmento.getLongitud() < (.8 * configuracion.getIntervalo())) {
							annotationText.setX(valorFinal - valorInicial);
							annotationText.setTextAnchor(TextAnchor.TOP_LEFT);
						}
					}
					if (segmentos.size() > 30) annotationText.setRotationAngle(-Math.PI / 2);
					plot.addAnnotation(annotationText, false);
	
				}
				if (configuracion.isMostrarAltitud()) {
					XYTextAnnotation annotationAltitud;
					double alturaTextoAltitud = segmento.getAlturaInicial() + alturaTexto - plot.getRangeAxis().getLowerBound();
					annotationAltitud = new XYTextAnnotation(String.valueOf(Math.round(segmento.getAlturaInicial())), valorX, alturaTextoAltitud);
					annotationAltitud.setFont(annotationAltitud.getFont().deriveFont(Font.PLAIN, 9f));
					annotationAltitud.setBackgroundPaint(Color.WHITE);
					if (segmentos.size() > 30) annotationAltitud.setRotationAngle(-Math.PI / 2);
					plot.addAnnotation(annotationAltitud, false);
					if (index == segmentos.size() - 1)  {
						alturaTextoAltitud = segmento.getAlturaFinal() + alturaTexto - plot.getRangeAxis().getLowerBound();
						annotationAltitud = new XYTextAnnotation(String.valueOf(Math.round(segmento.getAlturaFinal())), valorFinal - valorInicial, alturaTextoAltitud);
						annotationAltitud.setBackgroundPaint(Color.WHITE);
						annotationAltitud.setFont(annotationAltitud.getFont().deriveFont(Font.PLAIN, 9f));
						if (segmento.getLongitud() < (.8 * configuracion.getIntervalo())) {
							annotationAltitud.setY(segmento.getAlturaFinal());
							annotationAltitud.setX(valorFinal - valorInicial);
							annotationAltitud.setTextAnchor(TextAnchor.TOP_LEFT);
						}
						if (segmentos.size() > 30) annotationAltitud.setRotationAngle(-Math.PI / 2);
						plot.addAnnotation(annotationAltitud, false);
					}
				}				
			}
			if (configuracion.isMostrarLeyenda()) {
				double anchoTotal = plot.getDomainAxis().getUpperBound() -  plot.getDomainAxis().getLowerBound(); 
				DatosSegmentoBean datosSegmento = datosActividad.getDatosSegmentoBean();
				double posicionXLeyenda = segmentoMinimo;
				if (segmentoMinimo > segmentos.size() / 2) {
					posicionXLeyenda = segmentoMinimo - (anchoTotal / 10);
				}
				if (segmentos.size() < 2) posicionXLeyenda = plot.getDomainAxis().getLowerBound() + anchoTotal / 30;
				double alturaLeyenda0 = (plot.getRangeAxis().getUpperBound() * 29 + plot.getRangeAxis().getLowerBound() * 1) / 30;
				double alturaLeyenda1 = (plot.getRangeAxis().getUpperBound() * 28 + plot.getRangeAxis().getLowerBound() * 2) / 30;
				double alturaLeyenda2 = (plot.getRangeAxis().getUpperBound() * 27 + plot.getRangeAxis().getLowerBound() * 3) / 30;
				double alturaLeyenda3 = (plot.getRangeAxis().getUpperBound() * 26 + plot.getRangeAxis().getLowerBound() * 4) / 30;
				double alturaLeyenda4 = (plot.getRangeAxis().getUpperBound() * 25 + plot.getRangeAxis().getLowerBound() * 5) / 30;
				double alturaLeyenda5 = (plot.getRangeAxis().getUpperBound() * 24 + plot.getRangeAxis().getLowerBound() * 6) / 30;
				double alturaLeyenda6 = (plot.getRangeAxis().getUpperBound() * 23 + plot.getRangeAxis().getLowerBound() * 7) / 30;
				double alturaLeyenda7 = (plot.getRangeAxis().getUpperBound() * 22 + plot.getRangeAxis().getLowerBound() * 8) / 30;
				XYTextAnnotation desnivelAnnotation = new XYTextAnnotation("Desnivel: " + UtilidadesFormat.getIntegerFormat().format(datosSegmento.getDesnivelTotal()) + " m", posicionXLeyenda, alturaLeyenda1);
				XYTextAnnotation longitudAnnotation = new XYTextAnnotation("Longitud: " + UtilidadesFormat.getDecimalFormat().format((double)datosSegmento.getLongitud() / 1000.0) + " km", posicionXLeyenda, alturaLeyenda2);
				XYTextAnnotation altitudAnnotation = new XYTextAnnotation("Altitud: " + UtilidadesFormat.getIntegerFormat().format(datosSegmento.getAltitudMax()) + " m", posicionXLeyenda, alturaLeyenda3);
				XYTextAnnotation pendienteAnnotation = new XYTextAnnotation("Pte. Med: " + UtilidadesFormat.getPercentFormat().format(datosSegmento.getPendienteMed()), posicionXLeyenda, alturaLeyenda4);
				XYTextAnnotation pendienteMaxAnnotation = new XYTextAnnotation("Pte. Máx: " + UtilidadesFormat.getPercentFormat().format(datosSegmento.getPendienteMax()), posicionXLeyenda, alturaLeyenda5);
				XYTextAnnotation coeficienteAnnotation = new XYTextAnnotation("Coef. APM: " + UtilidadesFormat.getDecimalFormat().format(datosSegmento.getCoeficienteAPM()), posicionXLeyenda, alturaLeyenda6);
				
				TextAnchor ta = TextAnchor.BASELINE_LEFT;
				desnivelAnnotation.setTextAnchor(ta);
				longitudAnnotation.setTextAnchor(ta);
				altitudAnnotation.setTextAnchor(ta);
				pendienteAnnotation.setTextAnchor(ta);
				pendienteMaxAnnotation.setTextAnchor(ta);
				coeficienteAnnotation.setTextAnchor(ta);

				XYBoxAnnotation marcoAnnotation = new XYBoxAnnotation(posicionXLeyenda - (anchoTotal / 60), alturaLeyenda7, posicionXLeyenda + anchoTotal / 5, alturaLeyenda0, new BasicStroke(.5f), Color.BLACK, Color.WHITE);
				plot.addAnnotation(marcoAnnotation);
				
				plot.addAnnotation(desnivelAnnotation);
				plot.addAnnotation(longitudAnnotation);
				plot.addAnnotation(altitudAnnotation);
				plot.addAnnotation(pendienteAnnotation);
				plot.addAnnotation(pendienteMaxAnnotation);
				plot.addAnnotation(coeficienteAnnotation);
				
			}
		}
		datosActividad.setPuntoSeleccionado(datosActividad.getInicioRango());
		updatePuntoSeleccionado();
	}
	
	public int getTipo() {
		return (configuracion.isRelleno()?XYAreaRenderer.AREA:XYAreaRenderer.LINES);
	}
	
	public void updatePuntoSeleccionado() {
		ocultaValorSeleccionado();
		if (datosActividad.getPuntoSeleccionado() >= datosActividad.getInicioRango())  {
			EjeX ejeX = EjeX.getInstanciaEjeDistancia();
			double valorInicial = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(datosActividad.getInicioRango()), ejeX);
			TrackPoint trackPoint = datosActividad.getTrack().getPuntos().get(datosActividad.getPuntoSeleccionado());
			double valorX = datosActividad.getValorX(trackPoint, ejeX) - valorInicial;
			annotationAlt.setText(" " + UtilidadesFormat.getIntegerFormat().format(trackPoint.getAltitud()) + " m ");
			annotationAlt.setX(valorX);
			annotationAlt.setY((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2);
			annotationPte.setText(" " + UtilidadesFormat.getPercentFormat().format(trackPoint.getPendiente()));
			annotationPte.setX(valorX);
			annotationPte.setY((14 * plot.getRangeAxis().getUpperBound() + 16 * plot.getRangeAxis().getLowerBound() ) /30);
			annotationLine = new XYLineAnnotation(valorX, plot.getRangeAxis().getUpperBound(), valorX, plot.getRangeAxis().getLowerBound(), new BasicStroke(.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER), Color.BLACK);
			plot.addAnnotation(annotationLine);
			plot.addAnnotation(annotationAlt);		
			plot.addAnnotation(annotationPte);		
		}
	}
	
	public void ocultaValorSeleccionado()  {
		plot.removeAnnotation(annotationAlt);
		plot.removeAnnotation(annotationPte);
		plot.removeAnnotation(annotationLine);
	}
	
	public void setValorSeleccionado(double valorNuevo, String texto)  {

	}
	
	public void updateConfiguracion()  {
   		update();
	}
	
	public void chartMouseMoved(ChartMouseEvent evento) {
		if (evento.getEntity() instanceof XYItemEntity)  {
			XYItemEntity item = (XYItemEntity) evento.getEntity(); 
			datosActividad.setPuntoSeleccionado(item.getItem() + datosActividad.getInicioRango());
    		updatePuntoSeleccionado();
		}
	}

	public void chartMouseClicked(ChartMouseEvent evento) {
		if (evento.getTrigger().getClickCount() == 2 && evento.getSource() instanceof JFreeChart) {
			datosActividad.resetRango();
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}
	}

	public void axisChanged(AxisChangeEvent evento) {
		if (evento.getSource() instanceof ValueAxis)  {
			EjeX ejeX = EjeX.getInstanciaEjeDistancia();
			System.out.println("axisChanged:" + evento.getSource());
			double valorInicial = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(datosActividad.getInicioRango()), ejeX);
			double ejeXInicial = grafico.getXYPlot().getDomainAxis().getLowerBound();
			double ejeXFinal = grafico.getXYPlot().getDomainAxis().getUpperBound();
			datosActividad.setRangoByEjeX(ejeXInicial + valorInicial, ejeXFinal + valorInicial, ejeX);
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}
	}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {
		datosActividad.setPuntoSeleccionado(0);
		ocultaValorSeleccionado();
	}	

	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.CAMBIO_TITULO.equals(evento.getActionCommand())) {
			String nuevoTitulo = JOptionPane.showInputDialog("Nuevo título: ");
			if (nuevoTitulo != null && nuevoTitulo.length() > 0) grafico.getTitle().setText(nuevoTitulo);
		}
		if (ConstantesAcciones.PUNTO_FINAL.equals(evento.getActionCommand())) {
			PanelSelectorPunto punto = (PanelSelectorPunto) evento.getSource();
			datosActividad.setFinRango(punto.getPunto());
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}    	
	}
	
}

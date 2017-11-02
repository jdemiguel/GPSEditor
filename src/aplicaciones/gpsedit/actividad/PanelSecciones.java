package aplicaciones.gpsedit.actividad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryLineAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.Seccion;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionEjeRango;
import aplicaciones.gpsedit.config.ConfiguracionSecciones;
import aplicaciones.gpsedit.gps.TrackUtil;
import aplicaciones.gpsedit.listeners.ResizeDinamicoListener;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelSecciones extends JPanel implements ActionListener, ChartMouseListener, AxisChangeListener, MouseListener{

	private static final long serialVersionUID = -8519461951188633513L;
	private ChartPanel panelGrafico;

	private static final int POSICION_HR = 1;
	private static final int POSICION_CADENCIA = 2;
	private static final int POSICION_POTENCIA = 3;
	private static final int POSICION_VELOCIDAD = 0;
	
	private JFreeChart grafico;
	private CategoryPlot plot;
	private DefaultCategoryDataset datasetHR;
	private DefaultCategoryDataset datasetCadencia;
	private DefaultCategoryDataset datasetVelocidad;
	private DefaultCategoryDataset datasetPotencia;
	
	private DatosActividad datosActividad;
	
	private ConfiguracionSecciones configuracion;
	
	private	CategoryTextAnnotation annotationHR;
	private	CategoryTextAnnotation annotationCadencia;
	private	CategoryTextAnnotation annotationVelocidad;
	private	CategoryTextAnnotation annotationPotencia;
	private CategoryLineAnnotation annotationLine;
	
	private HashMap<String, Seccion> categorias = new HashMap<String, Seccion>();


	public PanelSecciones(ActionListener listener) {
		setLayout(null);
		setBounds(0, 0, 1300, 560);
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones();

		datasetHR = new DefaultCategoryDataset();
		datasetCadencia = new DefaultCategoryDataset();
		datasetVelocidad = new DefaultCategoryDataset();
		datasetPotencia = new DefaultCategoryDataset();

		annotationHR = new CategoryTextAnnotation("", "", 0);
		annotationCadencia = new CategoryTextAnnotation("", "", 0);
		annotationVelocidad = new CategoryTextAnnotation("", "", 0);
		annotationPotencia = new CategoryTextAnnotation("", "", 0);
		annotationLine = new CategoryLineAnnotation("", 0, "", 0, Color.BLACK, new BasicStroke(1));

		plot = new CategoryPlot();

        plot.setDataset(POSICION_HR, datasetHR);
		plot.setDataset(POSICION_CADENCIA, datasetCadencia);
		plot.setDataset(POSICION_VELOCIDAD, datasetVelocidad);
		plot.setDataset(POSICION_POTENCIA, datasetPotencia);

		plot.setDomainAxis(new CategoryAxis("Distancia"));
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		plot.getDomainAxis().setCategoryMargin(0);

		NumberAxis axisVelocidad = new NumberAxis("HR");
		NumberAxis axisHR = new NumberAxis("HR");
		axisHR.setNumberFormatOverride(UtilidadesFormat.getIntegerFormat());
		axisHR.setAutoRangeMinimumSize(30);
        NumberAxis axisCadencia = new NumberAxis("Cadencia");
        axisCadencia.setAutoRangeMinimumSize(30);
        axisCadencia.setNumberFormatOverride(UtilidadesFormat.getIntegerFormat());
        NumberAxis axisPotencia = new NumberAxis("Potencia");
        axisPotencia.setAutoRangeMinimumSize(30);
        axisPotencia.setNumberFormatOverride(UtilidadesFormat.getIntegerFormat());  
        
        plot.setRangeAxis(POSICION_HR, axisHR);
		plot.setRangeAxis(POSICION_VELOCIDAD, axisVelocidad);
        plot.setRangeAxis(POSICION_CADENCIA, axisCadencia);
        plot.setRangeAxis(POSICION_POTENCIA, axisPotencia);
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);
        plot.mapDatasetToRangeAxis(2, 2);
        plot.mapDatasetToRangeAxis(3, 3);

		grafico = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, false);
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
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_SECCIONES);
		jmenuItem.addActionListener(listener);
		menu.add(jmenuItem);
		
		add(panelGrafico);

	}
	
	public void updateConfiguracion()  {
		CategoryAxis eje = plot.getDomainAxis();
		if (configuracion.getEjeXSeccionAutomatica().isDistancia()) eje.setLabel("Distancia (km)");
		else if (configuracion.getEjeXSeccionAutomatica().isTiempoAbsoluto()) eje.setLabel("Tiempo absoluto (min)");
		else if (configuracion.getEjeXSeccionAutomatica().isTiempoMovimiento()) eje.setLabel("Tiempo total (min)");
		else if (configuracion.getEjeXSeccionAutomatica().isHora()) eje.setLabel("Hora");

		if (plot.getRangeAxisForDataset(POSICION_HR) instanceof NumberAxis) ((NumberAxis)plot.getRangeAxisForDataset(POSICION_HR)).setAutoRangeIncludesZero(configuracion.getEjeHR().isEjeCero());
		if (plot.getRangeAxisForDataset(POSICION_CADENCIA) instanceof NumberAxis) ((NumberAxis)plot.getRangeAxisForDataset(POSICION_CADENCIA)).setAutoRangeIncludesZero(configuracion.getEjeCadencia().isEjeCero());
		if (plot.getRangeAxisForDataset(POSICION_VELOCIDAD) instanceof NumberAxis) ((NumberAxis)plot.getRangeAxisForDataset(POSICION_VELOCIDAD)).setAutoRangeIncludesZero(configuracion.getEjeVelocidad().isEjeCero());
		if (plot.getRangeAxisForDataset(POSICION_POTENCIA) instanceof NumberAxis) ((NumberAxis)plot.getRangeAxisForDataset(POSICION_POTENCIA)).setAutoRangeIncludesZero(configuracion.getEjePotencia().isEjeCero());
		
		plot.setRenderer(POSICION_HR, getRenderer(configuracion.getEjeHR()));
		plot.setRenderer(POSICION_CADENCIA, getRenderer(configuracion.getEjeCadencia()));
		plot.setRenderer(POSICION_VELOCIDAD, getRenderer(configuracion.getEjeVelocidad()));
		plot.setRenderer(POSICION_POTENCIA, getRenderer(configuracion.getEjePotencia()));

		update();
	}
	
	public void update(DatosActividad datosActividad)  {
		this.datosActividad = datosActividad;
		updateConfiguracion();
	}
	

	
	private void update()  {
		
		datasetHR.clear();
		datasetCadencia.clear();
		datasetVelocidad.clear();
		datasetPotencia.clear();
		
		List<Seccion> secciones = datosActividad.getTrack().getSecciones();
		if (secciones.size() == 1) secciones = datosActividad.getSeccionesAutomaticas();
		
		plot.getDomainAxis().setTickLabelsVisible(secciones.size() < 50);

		if (datosActividad.getTrack().getTipoActividad().isPaso())  {
			DateAxis eje = new DateAxis("Paso");
			eje.setTimeZone(TimeZone.getTimeZone("GMT"));
			eje.setDateFormatOverride(UtilidadesFormat.getPasoFormat());
			eje.setAutoRangeMinimumSize(60000);
			plot.setRangeAxis(0, eje);
		} else {
			NumberAxis eje = new NumberAxis("Velocidad");
			plot.setRangeAxis(0, eje);
		}

		categorias.clear();
		for (int i=0; i<secciones.size(); i++ )  {
			Seccion seccion = secciones.get(i);
			DatosSegmentoBean datos = datosActividad.getDatos(seccion.getInicioRango(), seccion.getFinRango());
			categorias.put(seccion.getTexto(), seccion);
			datasetHR.addValue((double)datos.getHrMed(), "HR", seccion.getTexto());
			datasetCadencia.addValue((double)datos.getCadenciaMed(), "Cadencia", seccion.getTexto());
			datasetPotencia.addValue((double)datos.getPotenciaMed(), "Potencia", seccion.getTexto());
			if (datosActividad.getTrack().getTipoActividad().isPaso())  {
				datasetVelocidad.addValue((double)datos.getPasoMed(), "Paso", seccion.getTexto());
			} else {
				datasetVelocidad.addValue((double)datos.getVelocidadMed(), "Velocidad", seccion.getTexto());
			}
		}
	}
	
	public void ocultaValorSeleccionado()  {
		plot.removeAnnotation(annotationHR);
		plot.removeAnnotation(annotationCadencia);
		plot.removeAnnotation(annotationVelocidad);
		plot.removeAnnotation(annotationPotencia);
		plot.removeAnnotation(annotationLine);
	}
	
	public void setValorSeleccionado(Seccion seccion)  {
		DatosSegmentoBean datos = datosActividad.getDatos(seccion.getInicioRango(), seccion.getFinRango());
		StringBuffer textoHR = new StringBuffer("HR: " + UtilidadesFormat.getIntegerFormat().format(datos.getHrMed()) + " ppm");
		StringBuffer textoCadencia = new StringBuffer("Cadencia: " + UtilidadesFormat.getIntegerFormat().format(datos.getCadenciaMed()));
		StringBuffer textoPotencia = new StringBuffer("Potencia: " + UtilidadesFormat.getIntegerFormat().format(datos.getPotenciaMed()) + " W");
		StringBuffer textoVelocidad = new StringBuffer();
		if (datosActividad.getTrack().getTipoActividad().isPaso()) textoVelocidad.append("Paso: " + UtilidadesFormat.getPasoFormat().format(datos.getPasoMed()) + " min/km");
		else textoVelocidad.append("Velocidad: " + UtilidadesFormat.getDecimalFormat().format(datos.getVelocidadMed()) + " km/h");
		plot.removeAnnotation(annotationHR);
		plot.removeAnnotation(annotationCadencia);
		plot.removeAnnotation(annotationVelocidad);
		plot.removeAnnotation(annotationPotencia);
		plot.removeAnnotation(annotationLine);
		annotationHR = new CategoryTextAnnotation(textoHR.toString(), seccion.getTexto(), ((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2) * 1.15);
		annotationCadencia = new CategoryTextAnnotation(textoCadencia.toString(), seccion.getTexto(), ((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2));
		annotationVelocidad = new CategoryTextAnnotation(textoVelocidad.toString(), seccion.getTexto(), ((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2) * .85);
		annotationPotencia = new CategoryTextAnnotation(textoPotencia.toString(), seccion.getTexto(), ((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2) * .70);
		annotationLine = new CategoryLineAnnotation(seccion.getTexto(), ((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2) * .80, seccion.getTexto(), plot.getRangeAxis().getLowerBound(), Color.BLACK, new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		annotationHR.setFont(annotationHR.getFont().deriveFont(Font.BOLD, 16f));
		annotationCadencia.setFont(annotationHR.getFont().deriveFont(Font.BOLD, 16f));
		annotationVelocidad.setFont(annotationHR.getFont().deriveFont(Font.BOLD, 16f));
		annotationPotencia.setFont(annotationHR.getFont().deriveFont(Font.BOLD, 16f));
		plot.addAnnotation(annotationLine);
		if (configuracion.getEjeHR().isVisible()) plot.addAnnotation(annotationHR);
		if (configuracion.getEjeCadencia().isVisible()) plot.addAnnotation(annotationCadencia);
		if (configuracion.getEjePotencia().isVisible()) plot.addAnnotation(annotationPotencia);
		if (configuracion.getEjeVelocidad().isVisible()) plot.addAnnotation(annotationVelocidad);
	}
	
	public CategoryItemRenderer getRenderer(ConfiguracionEjeRango configuracion)  {
		CategoryItemRenderer renderer;
		if (configuracion.isTipoBarra()) {
			renderer = new BarRenderer();
			((BarRenderer)renderer).setItemMargin(0.05);
			((BarRenderer)renderer).setShadowVisible(false);
			((BarRenderer)renderer).setBarPainter(new StandardBarPainter());
			((BarRenderer)renderer).setDrawBarOutline(true);
			((BarRenderer)renderer).setSeriesOutlinePaint(0, Color.WHITE);
		} else if (configuracion.isRelleno()){
			renderer = new AreaRenderer();
		} else {
			renderer = new LineAndShapeRenderer(true, configuracion.isMostrarPunto());
		}
		renderer.setSeriesPaint(0, new Color(configuracion.getColor().getRed(), configuracion.getColor().getGreen(), configuracion.getColor().getBlue(), 127));
		renderer.setSeriesStroke(0, new BasicStroke(2f));
		renderer.setSeriesVisible(0, configuracion.isVisible());
		return renderer;
	}

	
	public void actionPerformed(ActionEvent evento) {

    }
	
	
	public void chartMouseMoved(ChartMouseEvent evento) {
		if (evento.getEntity() instanceof CategoryItemEntity)  {
			CategoryItemEntity item = (CategoryItemEntity) evento.getEntity();
			Seccion seccion = categorias.get(item.getColumnKey());
			setValorSeleccionado(seccion);
		}
	}

	public void chartMouseClicked(ChartMouseEvent evento) {
	}

	public void axisChanged(AxisChangeEvent evento) {
	}


	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {
		ocultaValorSeleccionado();
	}
}

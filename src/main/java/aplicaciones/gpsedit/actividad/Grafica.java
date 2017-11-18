package aplicaciones.gpsedit.actividad;

import java.awt.BasicStroke;
import java.awt.Color;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import aplicaciones.gpsedit.beans.DatosGraficaBean;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionEjeRango;
import aplicaciones.gpsedit.config.ConfiguracionGraficas;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class Grafica{

	public static final String EMPTY = "";
	private XYPlot plot;
	private String titulo;
	private ConfiguracionEjeRango configuracion;
	private ConfiguracionGraficas configuracionGraficas;

	private DatosActividad datosActividad;
	private DatosGraficaBean datosGrafica;
	private XYTextAnnotation annotation;
	private XYLineAnnotation annotationLine;
	
	//si cambia el rango recalculamos la suavidad
	private int tamRangoAnterior = 0;
	
	XYSeries serie = new XYSeries("Altitud");

	Grafica(String titulo, ConfiguracionEjeRango configuracion, ValueAxis eje)  {
		this.configuracion = configuracion;
		this.configuracionGraficas = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas();
		this.titulo = titulo;
		annotation = new XYTextAnnotation("", 0, 0);
		annotation.setBackgroundPaint(Color.WHITE);
		annotation.setOutlineVisible(true);
		annotation.setOutlineStroke(new BasicStroke(1));
		annotationLine = new XYLineAnnotation(0, 0, 0, 0);
		plot = new XYPlot();
		plot.setDomainCrosshairVisible(false);
		if (eje instanceof NumberAxis) {
			NumberAxis rangeAxis = (NumberAxis)eje;
			rangeAxis.setAutoRangeIncludesZero(false);
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setAutoRange(true);
			rangeAxis.setUpperMargin(.15);
			rangeAxis.setLowerMargin(.15);			
		}
		if (eje instanceof DateAxis) {
			DateAxis rangeAxis = (DateAxis)eje;
			rangeAxis.setDateFormatOverride(UtilidadesFormat.getPasoFormat());
			//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setAutoRange(true);
			rangeAxis.setUpperMargin(.15);
			rangeAxis.setLowerMargin(.15);			
		}
		eje.setLabel(titulo);
		plot.setRangeAxis(eje);
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		
	}
	
	public void update(DatosActividad datosActividad, DatosGraficaBean datosGrafica)  {
		this.datosActividad = datosActividad;
		this.datosGrafica = datosGrafica;
		//ajustamos la suavidad al tamaño del rango
		
		int tamRango = datosActividad.getFinRango() - datosActividad.getInicioRango();
		if (tamRango != this.tamRangoAnterior)  {
			this.tamRangoAnterior = tamRango;
			int nuevaSuavidad =  (int) Math.round((10000f * (double)tamRango) / (double)datosActividad.getTrack().getPuntos().size());
			if (nuevaSuavidad > 9) nuevaSuavidad = 9;
			if (nuevaSuavidad != configuracion.getAjusteSuavidad())  {
				configuracion.setAjusteSuavidad(nuevaSuavidad);
			}
		}
		update();
	}
	
	private void update()  {
		UnivariateFunction datos = null;
		annotation.setOutlinePaint(configuracion.getColor());
		if (configuracion.isSuavizado()) {
			datos = datosGrafica.getDatosSuavizados(configuracion.getAjusteSuavidad());
		} else {
			datos = datosGrafica.getDatosBrutos();
		}
		serie = new XYSeries(titulo);
		for (int i=datosActividad.getInicioRango(); i<= datosActividad.getFinRango(); i++)  {
			double valorX = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(i), configuracionGraficas.getEjeX());
			try  {
				serie.add(valorX, datos.value(valorX));
			} catch (Exception e)  {
				//se descarta el punto. Probablemente sea un punto sin info de altura situado al principio 
			}
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);
		plot.setDataset(dataset);
		
		XYItemRenderer renderer = new XYAreaRenderer(getTipo());
		renderer.setSeriesPaint(0, configuracion.getColor());
		renderer.setSeriesStroke(0, new BasicStroke (1f));
		plot.setRenderer(renderer);

		if (plot.getRangeAxis() instanceof NumberAxis) {
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setAutoRangeIncludesZero(configuracion.isEjeCero());
			rangeAxis.setAutoRange(true);
		}
	}
	
	public boolean isVisible()  {
		return configuracion.isVisible();
	}
	
	public XYPlot getPlot() {
		return plot;
	}

	public int getTipo() {
		return (configuracion.isRelleno()?XYAreaRenderer.AREA:XYAreaRenderer.LINES);
	}
	public void ocultaValorSeleccionado()  {
		plot.removeAnnotation(annotation);
		plot.removeAnnotation(annotationLine);
	}
	
	public void setValorSeleccionado(double valorNuevo, String texto)  {
		plot.removeAnnotation(annotation);
		plot.removeAnnotation(annotationLine);
		annotation.setText(" " + texto + " ");
		annotation.setX(valorNuevo);
		annotation.setY((plot.getRangeAxis().getUpperBound() + plot.getRangeAxis().getLowerBound() ) /2);
		annotationLine = new XYLineAnnotation(valorNuevo, plot.getRangeAxis().getUpperBound(), valorNuevo, plot.getRangeAxis().getLowerBound(), new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER), Color.BLACK);
		plot.addAnnotation(annotationLine);
		plot.addAnnotation(annotation);
	}
	
}

package aplicaciones.gpsedit.actividad;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.plot.CombinedDomainXYPlot;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionGraficas;
import aplicaciones.gpsedit.listeners.PopupGraficoAdapter;
import aplicaciones.gpsedit.listeners.ResizeDinamicoListener;
import aplicaciones.gpsedit.menus.EditMenu;
import aplicaciones.gpsedit.util.EjeX;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelGrafico extends JPanel implements ChartMouseListener, AxisChangeListener, MouseListener{

	private static final long serialVersionUID = -8519461951188633513L;
	private ChartPanel panelGrafico;
	private JFreeChart grafico;
	private CombinedDomainXYPlot plotPrincipal;
	private Grafica graficaAltitud;
	private Grafica graficaHR;
	private Grafica graficaCadencia;
	private Grafica graficaVelocidad;
	private Grafica graficaPendiente;
	private Grafica graficaPotencia;
	private ArrayList<Grafica> graficas;
	private NumberAxis ejeDistancia;
	private DateAxis ejeTiempoMovimiento;
	private DateAxis ejeTiempoAbsoluto;
	private DateAxis ejeHora;
	
	private DatosActividad datosActividad;
	
	private ConfiguracionGraficas configuracion;
	private EjeX ejeX;

	private PopupGraficoAdapter popupGraficoAdapter;
	private PanelActividad panelActividad;

	
	public PanelGrafico(PanelActividad panelActividad) {
		this.panelActividad = panelActividad;
		setLayout(null);
		setBounds(0, 0, 1300, 560);
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas();
		ejeX = configuracion.getEjeX();
		
		ejeDistancia = new NumberAxis("Distancia (km)");
		ejeTiempoMovimiento = new DateAxis("Tiempo en movimiento (horas)");
		ejeTiempoMovimiento.setTimeZone(TimeZone.getTimeZone("GMT"));
		ejeTiempoAbsoluto = new DateAxis("Tiempo (horas)");
		ejeTiempoAbsoluto.setTimeZone(TimeZone.getTimeZone("GMT"));
		ejeHora = new DateAxis("Hora");
		ejeHora.setTimeZone(TimeZone.getTimeZone("GMT"));//ha de ser GMT para que no nos afecte que sea verano o invierno al restarle el día (el 1 de enero es invierno)
		plotPrincipal = new CombinedDomainXYPlot(ejeDistancia);
		grafico = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plotPrincipal, false);
		grafico.setBackgroundPaint(Color.WHITE);

        graficas = new ArrayList<Grafica>();
        
		graficaAltitud = new Grafica("Alt.", configuracion.getEjeAltitud());
		graficaHR = new Grafica( "HR", configuracion.getEjeHR());
		graficaCadencia = new Grafica("Cad.", configuracion.getEjeCadencia());
		graficaVelocidad = new Grafica("Vel./Paso", configuracion.getEjeVelocidad());
		graficaPendiente= new Grafica("Pte.", configuracion.getEjePendiente());
		graficaPotencia = new Grafica("Watt", configuracion.getEjePotencia());
		
		graficas.add(graficaAltitud);
		graficas.add(graficaHR);
		graficas.add(graficaCadencia);
		graficas.add(graficaVelocidad);
		graficas.add(graficaPendiente);
		graficas.add(graficaPotencia);
		
		for (int i=0; i< graficas.size(); i++) plotPrincipal.add(graficas.get(i).getPlot());

		panelGrafico = new ChartPanel(grafico);
		panelGrafico.addMouseListener(this);
		panelGrafico.setDomainZoomable(true);
		panelGrafico.setRangeZoomable(false);
		panelGrafico.addChartMouseListener(this);
		
		panelGrafico.setBounds(0, 0, 1300, 50);
		panelGrafico.addHierarchyBoundsListener(new ResizeDinamicoListener());

		add(panelGrafico);
	}
	
	private void crearPopupMenu()  {
		JPopupMenu menu = panelGrafico.getPopupMenu();
		menu.removeAll();
		popupGraficoAdapter = new PopupGraficoAdapter(panelActividad);
		popupGraficoAdapter.setDatosActividad(datosActividad);

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
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_GRAFICAS);
		jmenuItem.addActionListener(panelActividad);
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
		menu.add(new Separator());
		menu.add(new EditMenu(panelActividad));		
	}
	
	public void update(DatosActividad datosActividad)  {
		this.datosActividad = datosActividad;
		crearPopupMenu();
		for (int i=0; i< graficas.size(); i++) plotPrincipal.remove(graficas.get(i).getPlot());
		for (int i=0; i< graficas.size(); i++) if(graficas.get(i).isVisible()) plotPrincipal.add(graficas.get(i).getPlot());
		update();
	}
	
	public void update()  {
		ValueAxis eje = getEjeX();
		double valorInicial = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(datosActividad.getInicioRango()), ejeX);
		double valorFinal = datosActividad.getValorX(datosActividad.getTrack().getPuntos().get(datosActividad.getFinRango()), ejeX);

		eje.removeChangeListener(this);
		eje.setLowerBound(valorInicial);
		eje.setUpperBound(valorFinal);

		grafico.getXYPlot().setDomainAxis(eje);
		
		graficaAltitud.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosAltitud());
		if (datosActividad.getTrack().getTipoActividad().isPaso()) graficaVelocidad.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosPaso());
		else graficaVelocidad.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosVelocidad());
		graficaHR.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosHR());
		graficaCadencia.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosCadencia());
		graficaPendiente.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosPendiente());
		graficaPotencia.update(datosActividad, datosActividad.getDatosGrafica(ejeX).getDatosPotencia());
		
		eje.addChangeListener(this);
		datosActividad.setPuntoSeleccionado(datosActividad.getInicioRango());
		updatePuntoSeleccionado();
	}
	
	public ValueAxis getEjeX()  {
    	if (ejeX.isDistancia()) return ejeDistancia;
    	else if (ejeX.isTiempoAbsoluto()) return ejeTiempoAbsoluto;
    	else if (ejeX.isTiempoMovimiento()) return ejeTiempoMovimiento;
    	return ejeHora;
	}
	
	public void updatePuntoSeleccionado() {
		if (datosActividad.getPuntoSeleccionado() > datosActividad.getInicioRango())  {
			TrackPoint trackPoint = datosActividad.getTrack().getPuntos().get(datosActividad.getPuntoSeleccionado());
			double valorX = datosActividad.getValorX(trackPoint, ejeX);
			graficaAltitud.setValorSeleccionado(valorX, UtilidadesFormat.getIntegerFormat().format(trackPoint.getAltitud()) + " m");
			graficaHR.setValorSeleccionado(valorX, UtilidadesFormat.getIntegerFormat().format(trackPoint.getHR()) + " bpm");
			graficaCadencia.setValorSeleccionado(valorX, UtilidadesFormat.getIntegerFormat().format(trackPoint.getCadencia()));    
			if (datosActividad.getTrack().getTipoActividad().isPaso()) graficaVelocidad.setValorSeleccionado(valorX, UtilidadesFormat.getPasoFormat().format(datosActividad.getPaso(datosActividad.getPuntoSeleccionado())) + " min/km");        
			else graficaVelocidad.setValorSeleccionado(valorX, UtilidadesFormat.getDecimalFormat().format(datosActividad.getVelocidad(datosActividad.getPuntoSeleccionado())) + " km/h");        
			graficaPendiente.setValorSeleccionado(valorX, UtilidadesFormat.getPercentFormat().format(datosActividad.getPendiente(datosActividad.getPuntoSeleccionado())));        
			graficaPotencia.setValorSeleccionado(valorX, UtilidadesFormat.getIntegerFormat().format(trackPoint.getPotencia()) + " W");        
		} else {
			for (int i=0; i< graficas.size(); i++) graficas.get(i).ocultaValorSeleccionado();
		}
	}
	
	public void updateConfiguracion()  {
   		for (int i=0; i< graficas.size(); i++) plotPrincipal.remove(graficas.get(i).getPlot());
   		for (int i=0; i< graficas.size(); i++) if(graficas.get(i).isVisible()) plotPrincipal.add(graficas.get(i).getPlot());
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
		System.out.println("src:" + evento.getSource());
		if (evento.getTrigger().getClickCount() == 2 && evento.getSource() instanceof JFreeChart) {
			datosActividad.resetRango();
			panelActividad.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}
	}

	public void axisChanged(AxisChangeEvent evento) {
		if (evento.getSource() instanceof ValueAxis)  {
			double ejeXInicial = grafico.getXYPlot().getDomainAxis().getLowerBound();
			double ejeXFinal = grafico.getXYPlot().getDomainAxis().getUpperBound();
			datosActividad.setRangoByEjeX(ejeXInicial, ejeXFinal, ejeX);
			panelActividad.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}
	}


	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {
		datosActividad.setPuntoSeleccionado(0);
		for (int i=0; i< graficas.size(); i++) graficas.get(i).ocultaValorSeleccionado();
	}
}

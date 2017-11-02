package aplicaciones.gpsedit.actividad;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.DialogConfiguracionCoeficientes;
import aplicaciones.gpsedit.config.DialogConfiguracionGraficas;
import aplicaciones.gpsedit.config.DialogConfiguracionMapa;
import aplicaciones.gpsedit.config.DialogConfiguracionSecciones;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelActividad extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
	
	private PanelMapaActividad panelMapa;
	private PanelGrafico panelGrafico;
	private PanelSecciones panelSecciones;
	private PanelCoeficientes panelCoeficientes;
	private PanelTablaDatosActividad panelTablaDatosSegmento;
	private PanelTablaDatosActividad panelTablaDatosTotal;
	private PanelTablaSecciones panelTablaSecciones;
	private PanelTablaCoeficientes panelTablaCoeficientes;
	private PanelDatosSegmento panelDatosSegmento;
	private PanelDatosActividad panelDatosActividad;
	private DialogConfiguracionGraficas dialogConfiguracionGraficas;
	private DialogConfiguracionSecciones dialogConfiguracionSecciones;
	private DialogConfiguracionCoeficientes dialogConfiguracionCoeficientes;
	private DialogConfiguracionMapa dialogConfiguracionMapa;
	private DialogProgreso dialogoProgreso;
	
	private DatosActividad datosActividad;

	
	private JTabbedPane panelesPrincipales;
	private JTabbedPane panelesDatos;

	private JLabel tituloLabel;
	
	public PanelActividad() {
		setBounds(0,0,1300,768);
		setLayout(null);


		JPanel panelTitulo = new JPanel();
		panelTitulo.setBounds(0,0,1300,25);
		panelTitulo.setLayout(new GridLayout(1,1));
        JPanel panelInterior = new JPanel();
        tituloLabel = new JLabel("");
        panelInterior.add(tituloLabel);
        panelTitulo.add(panelInterior);
		add(panelTitulo);
		
		dialogConfiguracionGraficas = new DialogConfiguracionGraficas(this);
		dialogConfiguracionSecciones = new DialogConfiguracionSecciones(this);
		dialogConfiguracionMapa = new DialogConfiguracionMapa(this);
		dialogConfiguracionCoeficientes = new DialogConfiguracionCoeficientes(this);
		dialogoProgreso = new DialogProgreso(this);
		dialogoProgreso.setLocationRelativeTo(this);


		panelTablaDatosSegmento = new PanelTablaDatosActividad(this, true);
		panelTablaDatosTotal = new PanelTablaDatosActividad(this, false);
		panelTablaSecciones = new PanelTablaSecciones(this);
		panelTablaCoeficientes = new PanelTablaCoeficientes();
		panelMapa = new PanelMapaActividad(this);
		panelGrafico = new PanelGrafico(this);
		panelSecciones = new PanelSecciones(this);
		panelCoeficientes = new PanelCoeficientes(this);
		
        panelDatosSegmento  =  new PanelDatosSegmento(this);
        panelDatosActividad  =  new PanelDatosActividad();

		panelesDatos = new JTabbedPane();

		panelesDatos.addTab("Datos de la Actividad", null, panelDatosActividad, "Datos de la Actividad");
		panelesDatos.addTab("Datos del Segmento Seleccionado", null, panelDatosSegmento, "Datos del Segmento Seleccionado");
		panelesDatos.addTab("Secciones", null, panelTablaSecciones, "Secciones de la actividad");
		panelesDatos.addTab("Tabla de Datos Segmento", null, panelTablaDatosSegmento, "Tabla de Datos del Segmento Seleccionado");
		panelesDatos.addTab("Tabla de Datos Total", null, panelTablaDatosTotal, "Tabla de Datos de toda la actividad");
		panelesDatos.addTab("Tabla de Coeficiente APM", null, panelTablaCoeficientes, "Tabla de Datos de cálculo de coeficiente APM");

		panelesPrincipales = new JTabbedPane();
		

		panelesPrincipales.addTab("Gráficas", null, panelGrafico, "Gráficas");
		panelesPrincipales.addTab("Secciones", null, panelSecciones, "Secciones");
		panelesPrincipales.addTab("Mapa", null, panelMapa, "Mapa");
		panelesPrincipales.addTab("APM", null, panelCoeficientes, "Coeficientes APM");

		panelesPrincipales.setFocusable(false);
		panelesDatos.setFocusable(false);


		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 25, 1300, 743);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		splitPane.setTopComponent(panelesPrincipales);		
		splitPane.setBottomComponent(panelesDatos);
		add(splitPane);
		
		panelesPrincipales.setMinimumSize(new Dimension(1300, 330));
		panelesDatos.setMinimumSize(new Dimension(1300, 140));
		splitPane.setDividerLocation(.8);
	}

	public void update(DatosActividad datosActividad) {
		this.datosActividad = datosActividad;
		dialogoProgreso.setDatosActividad(datosActividad);
		Track track = datosActividad.getTrack();
		DatosSegmentoBean datosTrack = datosActividad.getDatosTrack();
		if (datosTrack.getHrMed() == 0) {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeHR().setVisible(false);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones().getEjeHR().setVisible(false);
		} else {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeHR().setVisible(true);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones().getEjeHR().setVisible(true);
		}
		if (datosTrack.getCadenciaMed() == 0) {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeCadencia().setVisible(false);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones().getEjeCadencia().setVisible(false);
		} else {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeCadencia().setVisible(true);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones().getEjeCadencia().setVisible(true);
		}
		if (datosTrack.getPotenciaMed() == 0) {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjePotencia().setVisible(false);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones().getEjePotencia().setVisible(false);
		} else {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjePotencia().setVisible(true);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones().getEjePotencia().setVisible(true);
		}
		if (!track.isHayGPS() || datosTrack.getAltitudMed() == 0)  {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeAltitud().setVisible(false);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeAltitud().setRelleno(false);
		} else {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeAltitud().setVisible(true);
		}
		if (datosTrack.getPendienteMed() == 0)  {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjePendiente().setVisible(false);
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjeAltitud().setRelleno(false);
		} else {
			Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas().getEjePendiente().setVisible(true);
		}		
		StringBuffer titulo = new StringBuffer();
		if (datosActividad.getTrack().getNombre() != null) titulo.append(track.getNombre());
		if (titulo.length() > 0) titulo.append(" (" + UtilidadesFormat.getFechaFormat().format(datosTrack.getHoraInicio()) + ")");
		else titulo.append(UtilidadesFormat.getFechaFormat().format(datosTrack.getHoraInicio()));
		tituloLabel.setText(titulo.toString());
		panelTablaSecciones.update(datosActividad);
		panelDatosActividad.update(datosActividad);
		panelTablaDatosTotal.update(datosActividad);

		panelDatosSegmento.setRango(datosActividad.getInicioRango(), datosActividad.getFinRango());
		panelDatosSegmento.setLimitesInicioRango(0, datosActividad.getFinRango());
		panelDatosSegmento.setLimitesFinRango(0, datosActividad.getTrack().getPuntos().size() - 1);
		
		update();
	}
	
	public void update()  {
		panelDatosSegmento.update(datosActividad);
		panelGrafico.update(datosActividad);
		panelTablaDatosSegmento.update(datosActividad);
		panelMapa.update(datosActividad);
		panelSecciones.update(datosActividad);
		panelCoeficientes.update(datosActividad);
		panelTablaCoeficientes.update(datosActividad);

	}
	
	public void updateConfiguracionGraficas() {
		panelDatosSegmento.updateConfiguracion();
		panelGrafico.updateConfiguracion();
	}
	
	public void updateConfiguracionSecciones() {
		datosActividad.updateConfiguracion();
		panelSecciones.updateConfiguracion();
		panelTablaSecciones.update();
	}
	
	public void updateConfiguracionCoeficientes() {
		panelCoeficientes.updateConfiguracion();
		panelDatosActividad.update(datosActividad);
		panelDatosSegmento.updateConfiguracion();
		panelTablaCoeficientes.update(datosActividad);
	}	
	
	public void updateConfiguracionMapa() {
		panelMapa.updateConfiguracion();
	}

	public void actionPerformed(ActionEvent evento) {
    	if (ConstantesAcciones.CAMBIO_RANGO.equalsIgnoreCase(evento.getActionCommand()))  {
    		update();
    	}
    	if (ConstantesAcciones.CAMBIO_CONFIGURACION_GRAFICAS.equalsIgnoreCase(evento.getActionCommand()))  {
    		updateConfiguracionGraficas();
    	}    	
    	if (ConstantesAcciones.CAMBIAR_CONFIGURACION_GRAFICAS.equalsIgnoreCase(evento.getActionCommand()))  {
    		dialogConfiguracionGraficas.update();
    		dialogConfiguracionGraficas.setVisible(true);
    	}  
    	if (ConstantesAcciones.CAMBIO_CONFIGURACION_SECCIONES.equalsIgnoreCase(evento.getActionCommand()))  {
    		updateConfiguracionSecciones();
    	}    	
    	if (ConstantesAcciones.CAMBIAR_CONFIGURACION_SECCIONES.equalsIgnoreCase(evento.getActionCommand()))  {
    		dialogConfiguracionSecciones.update();
    		dialogConfiguracionSecciones.setVisible(true);
		}    	
    	if (ConstantesAcciones.CAMBIO_CONFIGURACION_COEFICIENTES.equalsIgnoreCase(evento.getActionCommand()))  {
    		updateConfiguracionCoeficientes();
    	}    	
    	if (ConstantesAcciones.CAMBIAR_CONFIGURACION_COEFICIENTES.equalsIgnoreCase(evento.getActionCommand()))  {
    		dialogConfiguracionCoeficientes.update();
    		dialogConfiguracionCoeficientes.setVisible(true);
    	}  
    	if (ConstantesAcciones.CAMBIO_CONFIGURACION_MAPA.equalsIgnoreCase(evento.getActionCommand()))  {
    		updateConfiguracionMapa();
    	}    	
    	if (ConstantesAcciones.CAMBIAR_CONFIGURACION_MAPA.equalsIgnoreCase(evento.getActionCommand()))  {
    		dialogConfiguracionMapa.update();
    		dialogConfiguracionMapa.setVisible(true);
		}    
    	if (ConstantesAcciones.CAMBIO_TRACK.equalsIgnoreCase(evento.getActionCommand()))  {
			update(datosActividad);
    	}    	    	
	}

	public DialogProgreso getDialogoProgreso() {
		return dialogoProgreso;
	}



}

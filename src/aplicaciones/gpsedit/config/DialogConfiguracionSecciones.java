package aplicaciones.gpsedit.config;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class DialogConfiguracionSecciones extends JDialog implements ActionListener{

	private static final long serialVersionUID = -3767182358091005886L;
	private ActionListener listener;
	private List<PanelConfiguracionRangoSecciones> panelesConfiguracion = new ArrayList<PanelConfiguracionRangoSecciones>();
	private JRadioButton distancia;
	private JRadioButton tiempoMovimiento;
	private JRadioButton tiempoAbsoluto;
	private JRadioButton hora;
	private JComboBox intervalo;
	private ConfiguracionSecciones configuracion;
	
	
	public DialogConfiguracionSecciones(ActionListener listener) {
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones();
		setBackground(Color.WHITE);
		this.listener = listener;
		setTitle("Configuración Gráficas");
		setLayout(null);
		setBounds(0, 0, 380, 540);
		setModal(true);
		JPanel panelPrincipal = new JPanel(new GridLayout(5,1));
		panelPrincipal.setBounds(0, 0, 380, 440);
		
		
        JPanel panelSeccionesAutomaticas  =  new JPanel(new GridLayout(1, 3));
        panelSeccionesAutomaticas.setBorder(BorderFactory.createTitledBorder("Secciones Automáticas"));
        panelSeccionesAutomaticas.setBackground(UtilidadesSwing.COLOR_FONDO);
        panelPrincipal.add(panelSeccionesAutomaticas);
		
		distancia = new JRadioButton("", true);
		distancia.addActionListener(this);
		distancia.setActionCommand(ConstantesAcciones.TIPO);
		tiempoMovimiento = new JRadioButton("", false);
		tiempoMovimiento.addActionListener(this);
		tiempoMovimiento.setActionCommand(ConstantesAcciones.TIPO);
		tiempoAbsoluto = new JRadioButton("", false);
		tiempoAbsoluto.addActionListener(this);
		tiempoAbsoluto.setActionCommand(ConstantesAcciones.TIPO);
		hora = new JRadioButton("", false);
		hora.addActionListener(this);
		hora.setActionCommand(ConstantesAcciones.TIPO);
		ButtonGroup variableDominio = new ButtonGroup();
		variableDominio.add(distancia);
		variableDominio.add(tiempoMovimiento);
		variableDominio.add(tiempoAbsoluto);
		variableDominio.add(hora);
		
		intervalo = new JComboBox();

		panelSeccionesAutomaticas.add(UtilidadesSwing.createBloque2x1("Dist.", distancia, "Tmp. abs", tiempoAbsoluto, BorderFactory.createEmptyBorder()));
		panelSeccionesAutomaticas.add(UtilidadesSwing.createBloque2x1("Hora", hora, "Tmp. mov", tiempoMovimiento, BorderFactory.createEmptyBorder()));
        panelSeccionesAutomaticas.add(UtilidadesSwing.createBloque1x2("Inc.", intervalo));

		
		panelesConfiguracion.add(new PanelConfiguracionRangoSecciones("HR", configuracion.getEjeHR()));
		panelesConfiguracion.add(new PanelConfiguracionRangoSecciones("Cadencia", configuracion.getEjeCadencia()));
		panelesConfiguracion.add(new PanelConfiguracionRangoSecciones("Vel./Paso", configuracion.getEjeVelocidad()));
		panelesConfiguracion.add(new PanelConfiguracionRangoSecciones("Potencia", configuracion.getEjePotencia()));
		
		for (PanelConfiguracionRangoSecciones panelConfiguracion:panelesConfiguracion) panelPrincipal.add(panelConfiguracion);
		
		JPanel botones = new JPanel();
		botones.setBounds(0, 440, 380, 40);
		botones.setLayout(new FlowLayout());
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(this);
		aceptar.setActionCommand(ConstantesAcciones.ACEPTAR);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setActionCommand(ConstantesAcciones.CANCELAR);
		cancelar.addActionListener(this);
		botones.add(aceptar);
		botones.add(cancelar);
		
		getContentPane().add(panelPrincipal);
		getContentPane().add(botones);
		
	}
	
	public void update()  {
		for (PanelConfiguracionRangoSecciones panelConfiguracion:panelesConfiguracion) {
			panelConfiguracion.update();
		}		
		if (configuracion.getEjeXSeccionAutomatica().isDistancia()) distancia.setSelected(true);
		else if (configuracion.getEjeXSeccionAutomatica().isTiempoMovimiento()) tiempoMovimiento.setSelected(true);
		else if (configuracion.getEjeXSeccionAutomatica().isTiempoAbsoluto()) tiempoAbsoluto.setSelected(true);
		else if (configuracion.getEjeXSeccionAutomatica().isHora()) hora.setSelected(true);
		updatecomboIntervalo();
		for (int i=0; i< intervalo.getItemCount(); i++) if (((Item)intervalo.getItemAt(i)).getNumero() == configuracion.getIntervaloSeccionAutomatica()) intervalo.setSelectedIndex(i);
		
	}
	
	public void updatecomboIntervalo()  {
		if (distancia.isSelected()) {
			intervalo.removeAllItems();
			intervalo.addItem(new Item(1, "1 km"));
			intervalo.addItem(new Item(2, "2 km"));
			intervalo.addItem(new Item(5, "5 km"));
			intervalo.addItem(new Item(10, "10 km"));
			intervalo.addItem(new Item(20, "20 km"));
		} else {
			intervalo.removeAllItems();
			intervalo.addItem(new Item(60000, "1 min"));
			intervalo.addItem(new Item(300000, "5 min"));
			intervalo.addItem(new Item(600000, "10 min"));
			intervalo.addItem(new Item(1800000, "30 min"));
			intervalo.addItem(new Item(3600000, "1 hora"));
		}
	}
	
	
	
	public void actionPerformed(ActionEvent evento) {
    	if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand()))  {
			for (PanelConfiguracionRangoSecciones panelConfiguracion:panelesConfiguracion) {
				panelConfiguracion.actionPerformed(evento);
			}
			if (distancia.isSelected()) configuracion.getEjeXSeccionAutomatica().setDistancia();
			else if (tiempoMovimiento.isSelected()) configuracion.getEjeXSeccionAutomatica().setTiempoMovimiento();
			else if (tiempoAbsoluto.isSelected()) configuracion.getEjeXSeccionAutomatica().setTiempoAbsoluto();
			else if (hora.isSelected()) configuracion.getEjeXSeccionAutomatica().setHora();
			configuracion.setIntervaloSeccionAutomatica(((Item) intervalo.getSelectedItem()).getNumero());			
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_CONFIGURACION_SECCIONES));
			setVisible(false);
		}
    	if (ConstantesAcciones.CANCELAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		update();
    		setVisible(false);
    	}
    	if (ConstantesAcciones.TIPO.equalsIgnoreCase(evento.getActionCommand()))  {
    		updatecomboIntervalo();
    	}    	
	}
	
	public class Item {
		private int numero;
		private String texto;
		public Item(int numero, String texto) {
			this.numero = numero;
			this.texto = texto;
		}
		public int getNumero() {
			return numero;
		}
		public String toString() {
			return texto;
		}
	}

}

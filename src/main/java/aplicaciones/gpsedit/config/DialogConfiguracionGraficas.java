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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class DialogConfiguracionGraficas extends JDialog implements ActionListener{

	private static final long serialVersionUID = -3767182358091005886L;
	private ActionListener listener;
	private List<PanelConfiguracionRangoGraficas> panelesConfiguracion = new ArrayList<PanelConfiguracionRangoGraficas>();
	private JRadioButton distancia;
	private JRadioButton tiempoMovimiento;
	private JRadioButton tiempoAbsoluto;
	private JRadioButton hora;
	private JSpinner incrementoRango;
	private ConfiguracionGraficas configuracion;
	
	public DialogConfiguracionGraficas(ActionListener listener) {
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas();
		setBackground(Color.WHITE);
		this.listener = listener;
		setTitle("Configuración Gráficas");
		setLayout(null);
		setBounds(0, 0, 380, 740);
		setModal(true);
		JPanel panelPrincipal = new JPanel(new GridLayout(7,1));
		panelPrincipal.setBounds(0, 0, 380, 640);
		
		
        JPanel panelEjeX  =  new JPanel(new GridLayout(1, 3));
        panelEjeX.setBorder(BorderFactory.createTitledBorder("Eje X"));
        panelEjeX.setBackground(UtilidadesSwing.COLOR_FONDO);
        panelPrincipal.add(panelEjeX);
		
		distancia = new JRadioButton("", true);
		tiempoMovimiento = new JRadioButton("", false);
		tiempoAbsoluto = new JRadioButton("", false);
		hora = new JRadioButton("", false);
		ButtonGroup variableDominio = new ButtonGroup();
		variableDominio.add(distancia);
		variableDominio.add(tiempoMovimiento);
		variableDominio.add(tiempoAbsoluto);
		variableDominio.add(hora);
		
		incrementoRango = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        DefaultEditor editor = (DefaultEditor) incrementoRango.getEditor();
        editor.getTextField().setColumns(3);

        panelEjeX.add(UtilidadesSwing.createBloque2x1("Dist.", distancia, "Tmp. abs", tiempoAbsoluto, BorderFactory.createEmptyBorder()));
        panelEjeX.add(UtilidadesSwing.createBloque2x1("Hora", hora, "Tmp. mov", tiempoMovimiento, BorderFactory.createEmptyBorder()));
        panelEjeX.add(UtilidadesSwing.createBloque2x1("Inc.", incrementoRango, "", new JLabel(""), BorderFactory.createEmptyBorder()));

		panelesConfiguracion.add(new PanelConfiguracionRangoGraficas("Altitud", configuracion.getEjeAltitud()));
		panelesConfiguracion.add(new PanelConfiguracionRangoGraficas("HR", configuracion.getEjeHR()));
		panelesConfiguracion.add(new PanelConfiguracionRangoGraficas("Cadencia", configuracion.getEjeCadencia()));
		panelesConfiguracion.add(new PanelConfiguracionRangoGraficas("Vel./Paso", configuracion.getEjeVelocidad()));
		panelesConfiguracion.add(new PanelConfiguracionRangoGraficas("Pendiente", configuracion.getEjePendiente()));
		panelesConfiguracion.add(new PanelConfiguracionRangoGraficas("Potencia", configuracion.getEjePotencia()));
		
		for (PanelConfiguracionRangoGraficas panelConfiguracion:panelesConfiguracion) panelPrincipal.add(panelConfiguracion);
		
		JPanel botones = new JPanel();
		botones.setBounds(0, 640, 380, 40);
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
		for (PanelConfiguracionRangoGraficas panelConfiguracion:panelesConfiguracion) {
			panelConfiguracion.update();
		}		
		if (configuracion.getEjeX().isDistancia()) distancia.setSelected(true);
		else if (configuracion.getEjeX().isTiempoMovimiento()) tiempoMovimiento.setSelected(true);
		else if (configuracion.getEjeX().isTiempoAbsoluto()) tiempoAbsoluto.setSelected(true);
		else if (configuracion.getEjeX().isHora()) hora.setSelected(true);
        incrementoRango.setValue(configuracion.getIncrementoRango());
	}
	
	public void actionPerformed(ActionEvent evento) {
    	if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand()))  {
			for (PanelConfiguracionRangoGraficas panelConfiguracion:panelesConfiguracion) {
				panelConfiguracion.actionPerformed(evento);
			}
			if (distancia.isSelected()) configuracion.getEjeX().setDistancia();
			else if (tiempoMovimiento.isSelected()) configuracion.getEjeX().setTiempoMovimiento();
			else if (tiempoAbsoluto.isSelected()) configuracion.getEjeX().setTiempoAbsoluto();
			else if (hora.isSelected()) configuracion.getEjeX().setHora();
			configuracion.setIncrementoRango((Integer) incrementoRango.getValue());
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_CONFIGURACION_GRAFICAS));
		}
    	if (ConstantesAcciones.CANCELAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		update();
    	}
		setVisible(false);
	}
	
}

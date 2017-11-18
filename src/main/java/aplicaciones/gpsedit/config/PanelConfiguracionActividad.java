package aplicaciones.gpsedit.config;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelConfiguracionActividad extends JPanel implements ActionListener, ChangeListener {

	private static final long serialVersionUID = -3497924358756162699L;
	private ConfiguracionSecciones configuracion;
	private JRadioButton distancia;
	private JRadioButton tiempoMovimiento;
	private JRadioButton tiempoAbsoluto;
	private JSpinner intervaloSeccionAutomatica;
	private ButtonGroup variableEje; 
	private ActionListener listener;

	public PanelConfiguracionActividad(ActionListener listener)  {
		this.listener = listener;
		this.configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionSecciones();
		setLayout(new GridLayout(1, 4));
        setBorder(BorderFactory.createTitledBorder("Configuración Secciones Automáticas"));

		distancia = new JRadioButton("", true);
		distancia.addActionListener(this);
		tiempoMovimiento = new JRadioButton("", false);
		tiempoMovimiento.addActionListener(this);
		tiempoAbsoluto = new JRadioButton("", false);
		tiempoAbsoluto.addActionListener(this);
		variableEje = new ButtonGroup();
		variableEje.add(distancia);
		variableEje.add(tiempoMovimiento);
		variableEje.add(tiempoAbsoluto);
		
		intervaloSeccionAutomatica = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		intervaloSeccionAutomatica.addChangeListener(this);
        DefaultEditor editor = (DefaultEditor) intervaloSeccionAutomatica.getEditor();
        editor.getTextField().setColumns(2); 

        add(UtilidadesSwing.createBloque1x2("Distancia", distancia));
        add(UtilidadesSwing.createBloque1x2("Tiempo absoluto", tiempoAbsoluto));
        add(UtilidadesSwing.createBloque1x2("Tiempo movimiento", tiempoMovimiento));
        add(UtilidadesSwing.createBloque1x2("Intervalo", intervaloSeccionAutomatica));

		
	}
	public void actionPerformed(ActionEvent evento) {
		if (distancia.isSelected()) configuracion.getEjeXSeccionAutomatica().setDistancia();
		else if (tiempoMovimiento.isSelected()) configuracion.getEjeXSeccionAutomatica().setTiempoMovimiento();
		else if (tiempoAbsoluto.isSelected()) configuracion.getEjeXSeccionAutomatica().setTiempoAbsoluto();
		listener.actionPerformed(new ActionEvent(evento.getSource(), ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_CONFIGURACION_GRAFICAS));
	}

	public void stateChanged(ChangeEvent evento) {
		configuracion.setIntervaloSeccionAutomatica((Integer) intervaloSeccionAutomatica.getValue());
		listener.actionPerformed(new ActionEvent(evento.getSource(), ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_CONFIGURACION_GRAFICAS));
	}
}

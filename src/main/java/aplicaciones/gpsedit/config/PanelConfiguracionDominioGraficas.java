package aplicaciones.gpsedit.config;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelConfiguracionDominioGraficas extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3807300791120058901L;
	
	private JRadioButton distancia;
	private JRadioButton tiempoMovimiento;
	private JRadioButton tiempoAbsoluto;
	private JRadioButton hora;
	private JSpinner incrementoRango;
	
	private ConfiguracionGraficas configuracion;

	PanelConfiguracionDominioGraficas()  {
        setBackground(UtilidadesSwing.COLOR_FONDO);
        JPanel panel  =  new JPanel(new GridLayout(1, 3));
        panel.setBorder(BorderFactory.createTitledBorder("Configuración Eje X"));
        panel.setBackground(UtilidadesSwing.COLOR_FONDO);
        add(panel);
		
        configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas();

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
        editor.getTextField().setColumns(1);

        panel.add(UtilidadesSwing.createBloque2x1("Dist.", distancia, "Tmp. abs", tiempoAbsoluto, BorderFactory.createEmptyBorder()));
        panel.add(UtilidadesSwing.createBloque2x1("Hora", hora, "Tmp. mov", tiempoMovimiento, BorderFactory.createEmptyBorder()));
        panel.add(UtilidadesSwing.createBloque2x1("Inc.", incrementoRango, "", new JLabel(""), BorderFactory.createEmptyBorder()));

	}
	
	
	
	public void update()  {
		if (configuracion.getEjeX().isDistancia()) distancia.setSelected(true);
		else if (configuracion.getEjeX().isTiempoMovimiento()) tiempoMovimiento.setSelected(true);
		else if (configuracion.getEjeX().isTiempoAbsoluto()) tiempoAbsoluto.setSelected(true);
		else if (configuracion.getEjeX().isHora()) hora.setSelected(true);
        incrementoRango.setValue(configuracion.getIncrementoRango());
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand())) {
			if (distancia.isSelected()) configuracion.getEjeX().setDistancia();
			else if (tiempoMovimiento.isSelected()) configuracion.getEjeX().setTiempoMovimiento();
			else if (tiempoAbsoluto.isSelected()) configuracion.getEjeX().setTiempoAbsoluto();
			else if (hora.isSelected()) configuracion.getEjeX().setHora();
			configuracion.setIncrementoRango((Integer) incrementoRango.getValue());
		}
	}
}

package aplicaciones.gpsedit.config;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelConfiguracionRangoGraficas extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = -3767182358091005886L;
	
	private JDialog dialogoColor;
	private JPanel botonColor;
	private JColorChooser selectorColor;
	private JCheckBox visible; 
	private JSpinner ajusteSuavidad;
	private JCheckBox suavizar;
	private JCheckBox relleno;
	private JCheckBox ejeCero;
	private Color color;
	private ConfiguracionEjeRango configuracion;

	
	PanelConfiguracionRangoGraficas(String titulo, ConfiguracionEjeRango configuracion) {
		this.configuracion = configuracion;
        setBackground(UtilidadesSwing.COLOR_FONDO);
        JPanel panel  =  new JPanel(new GridLayout(1, 3));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.setBackground(UtilidadesSwing.COLOR_FONDO);
        add(panel);
		this.color = configuracion.getColor();

        visible = new JCheckBox("", true);
        relleno = new JCheckBox("", configuracion.isRelleno());
        ejeCero = new JCheckBox("", false);
        
        botonColor = new JPanel();
        botonColor.setName(ConstantesAcciones.COLOR);
        botonColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonColor.setSize(20, 20);
        botonColor.setBackground(configuracion.getColor());
        botonColor.addMouseListener(this);
        selectorColor = new JColorChooser(configuracion.getColor());
		selectorColor.setPreviewPanel(new JPanel());
		dialogoColor = JColorChooser.createDialog(botonColor, "Elige color de la gráfica " + titulo, true, selectorColor, this, null);
        suavizar = new JCheckBox("", configuracion.isSuavizado());
        suavizar.addActionListener(this);
        suavizar.setActionCommand(ConstantesAcciones.SUAVIZAR);
        ajusteSuavidad = new JSpinner(new SpinnerNumberModel(configuracion.getAjusteSuavidad(), 0, 9, 1));
        ajusteSuavidad.setName(ConstantesAcciones.SUAVIDAD);
        ajusteSuavidad.setEnabled(configuracion.isSuavizado());
        DefaultEditor editor = (DefaultEditor) ajusteSuavidad.getEditor();
        editor.getTextField().setColumns(1);

        panel.add(UtilidadesSwing.createBloque2x1("Visible", visible, "Relleno", relleno, BorderFactory.createEmptyBorder()));
        panel.add(UtilidadesSwing.createBloque2x1("Color", botonColor, "Ver Cero", ejeCero, BorderFactory.createEmptyBorder()));
        panel.add(UtilidadesSwing.createBloque2x1("Suavizar", suavizar, "ajuste", ajusteSuavidad, BorderFactory.createLineBorder(Color.BLACK)));
	}

	public void update()  {
		color = configuracion.getColor();
        visible.setSelected(configuracion.isVisible());
        ejeCero.setSelected(configuracion.isEjeCero());
        relleno.setSelected(configuracion.isRelleno());
        suavizar.setSelected(configuracion.isSuavizado());
        ajusteSuavidad.setValue(configuracion.getAjusteSuavidad());
        botonColor.setBackground(color);
        selectorColor.setColor(color);
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand())) {
			configuracion.setAjusteSuavidad((Integer) ajusteSuavidad.getValue());
			configuracion.setEjeCero(ejeCero.isSelected());
			configuracion.setRelleno(relleno.isSelected());
			configuracion.setSuavizado(suavizar.isSelected());
			configuracion.setVisible(visible.isSelected());
			configuracion.setColor(this.color);
		}
		if (ConstantesAcciones.OK.equalsIgnoreCase(evento.getActionCommand())) {
			this.color = selectorColor.getColor();
			botonColor.setBackground(color);
		}		
		if (ConstantesAcciones.SUAVIZAR.equalsIgnoreCase(evento.getActionCommand())) {
			ajusteSuavidad.setEnabled(suavizar.isSelected());
		}		
	}

	public void mouseClicked(MouseEvent evento) {
		if (evento.getSource() instanceof JPanel)  {
			JPanel source = (JPanel) evento.getSource();
			if (ConstantesAcciones.COLOR.equalsIgnoreCase(source.getName())) dialogoColor.setVisible(true);
		}
	}

	public void mousePressed(MouseEvent evento) {
	}

	public void mouseReleased(MouseEvent evento) {
	}

	public void mouseEntered(MouseEvent evento) {
	}

	public void mouseExited(MouseEvent evento) {
	}
	
}

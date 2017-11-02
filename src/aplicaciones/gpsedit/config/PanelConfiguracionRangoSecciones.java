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

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelConfiguracionRangoSecciones extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = -3767182358091005886L;
	
	private JDialog dialogoColor;
	private JPanel botonColor;
	private JColorChooser selectorColor;
	private JCheckBox tipoBarra;
	private JCheckBox visible; 
	private JCheckBox mostrarPunto;
	private JCheckBox relleno;
	private JCheckBox ejeCero;
	private Color color;
	private ConfiguracionEjeRango configuracion;

	
	PanelConfiguracionRangoSecciones(String titulo, ConfiguracionEjeRango configuracion) {
		this.configuracion = configuracion;
        setBackground(UtilidadesSwing.COLOR_FONDO);
        JPanel panel  =  new JPanel(new GridLayout(1, 3));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.setBackground(UtilidadesSwing.COLOR_FONDO);
        add(panel);
		this.color = configuracion.getColor();

        visible = new JCheckBox("", true);
        visible.addActionListener(this);
        relleno = new JCheckBox("", configuracion.isRelleno());
        relleno.addActionListener(this);
        relleno.setEnabled(!configuracion.isTipoBarra());
        ejeCero = new JCheckBox("", false);
        ejeCero.addActionListener(this);
        ejeCero.setEnabled(!configuracion.isTipoBarra());
        
        botonColor = new JPanel();
        botonColor.setName(ConstantesAcciones.COLOR);
        botonColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonColor.setSize(20, 20);
        botonColor.setBackground(configuracion.getColor());
        botonColor.addMouseListener(this);
        selectorColor = new JColorChooser(configuracion.getColor());
		selectorColor.setPreviewPanel(new JPanel());
		dialogoColor = JColorChooser.createDialog(botonColor, "Elige color de la gráfica " + titulo, true, selectorColor, this, null);
        tipoBarra = new JCheckBox("", configuracion.isSuavizado());
        tipoBarra.addActionListener(this);
        tipoBarra.setActionCommand(ConstantesAcciones.TIPO);
        mostrarPunto = new JCheckBox("", configuracion.isMostrarPunto());
        mostrarPunto.addActionListener(this);
        mostrarPunto.setActionCommand(ConstantesAcciones.SUAVIZAR);
        mostrarPunto.setEnabled(!configuracion.isTipoBarra());

        panel.add(UtilidadesSwing.createBloque2x1("Ver como barra", tipoBarra, "Visible", visible, BorderFactory.createEmptyBorder()));
        panel.add(UtilidadesSwing.createBloque2x1("Color", botonColor, "Ver Puntos", mostrarPunto, BorderFactory.createEmptyBorder()));
        panel.add(UtilidadesSwing.createBloque2x1("Ver Cero", ejeCero, "Relleno", relleno, BorderFactory.createEmptyBorder()));

		
	}

	public void update()  {
		color = configuracion.getColor();
        visible.setSelected(configuracion.isVisible());
        ejeCero.setSelected(configuracion.isEjeCero());
        relleno.setSelected(configuracion.isRelleno());
        mostrarPunto.setSelected(configuracion.isMostrarPunto());
        tipoBarra.setSelected(configuracion.isTipoBarra());
        botonColor.setBackground(color);
        selectorColor.setColor(color);
        
        relleno.setEnabled(!configuracion.isTipoBarra());
        ejeCero.setEnabled(!configuracion.isTipoBarra());
        mostrarPunto.setEnabled(!configuracion.isTipoBarra());
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand())) {
			configuracion.setEjeCero(ejeCero.isSelected());
			configuracion.setRelleno(relleno.isSelected());
			configuracion.setMostrarPunto(mostrarPunto.isSelected());
			configuracion.setTipoBarra(tipoBarra.isSelected());
			configuracion.setVisible(visible.isSelected());
			configuracion.setColor(this.color);
		}
		if (ConstantesAcciones.OK.equalsIgnoreCase(evento.getActionCommand())) {
			this.color = selectorColor.getColor();
			botonColor.setBackground(color);
		}		
		if (ConstantesAcciones.TIPO.equalsIgnoreCase(evento.getActionCommand())) {
			relleno.setEnabled(!tipoBarra.isSelected());
	        ejeCero.setEnabled(!tipoBarra.isSelected());
	        mostrarPunto.setEnabled(!tipoBarra.isSelected());
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

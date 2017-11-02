package aplicaciones.gpsedit.config;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class DialogConfiguracionCoeficientes extends JDialog implements ActionListener, MouseListener{

	private static final long serialVersionUID = -3767182358091005886L;
	private ActionListener listener;
	private JSpinner intervalo;
	private JDialog dialogoColorLinea;
	private JPanel botonColorLinea;
	private Color colorLinea;
	private JColorChooser selectorColorLinea;
	private JDialog dialogoColorRelleno;
	private JPanel botonColorRelleno;
	private Color colorRelleno;
	private JColorChooser selectorColorRelleno;
	private JCheckBox mostrarLineasPrincipales; 
	private JCheckBox mostrarLineasSecundarias; 
	private JCheckBox mostrarPendiente; 
	private JCheckBox mostrarAltitud; 
	private JCheckBox mostrarLeyenda; 
	private JSpinner ajusteSuavidad;
	private JCheckBox suavizar;
	private JCheckBox relleno;
	private JCheckBox ejeCero;

	
	private ConfiguracionCoeficientes configuracion;
	
	
	
	public DialogConfiguracionCoeficientes(ActionListener listener) {
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionCoeficientes();
		setBackground(Color.WHITE);
		this.listener = listener;
		setTitle("Configuración Coeficientes");
		setLayout(null);
		setBounds(0, 0, 380, 440);
		setModal(true);
		JPanel panelPrincipal = new JPanel(new GridLayout(3,2));
		panelPrincipal.setBounds(0, 0, 380, 340);
		
		
        
		this.colorLinea = configuracion.getColorLinea();
		this.colorRelleno = configuracion.getColorRelleno();

        relleno = new JCheckBox("", false);
        ejeCero = new JCheckBox("", false);
        mostrarLineasPrincipales = new JCheckBox("", false);
        mostrarLineasSecundarias = new JCheckBox("", false);
        mostrarPendiente = new JCheckBox("", false);
        mostrarAltitud = new JCheckBox("", false);
        mostrarLeyenda = new JCheckBox("", false);
        
        botonColorLinea = new JPanel();
        botonColorLinea.setName(ConstantesAcciones.COLOR_LINEA);
        botonColorLinea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonColorLinea.setSize(20, 20);
        botonColorLinea.setBackground(configuracion.getColorLinea());
        botonColorLinea.addMouseListener(this);
        selectorColorLinea = new JColorChooser(configuracion.getColorLinea());
		selectorColorLinea.setPreviewPanel(new JPanel());
		dialogoColorLinea = JColorChooser.createDialog(botonColorLinea, "Elige color de las líneas", true, selectorColorLinea, this, null);
        botonColorRelleno = new JPanel();
        botonColorRelleno.setName(ConstantesAcciones.COLOR_RELLENO);
        botonColorRelleno.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonColorRelleno.setSize(20, 20);
        botonColorRelleno.setBackground(configuracion.getColorRelleno());
        botonColorRelleno.addMouseListener(this);
        selectorColorRelleno = new JColorChooser(configuracion.getColorRelleno());
		selectorColorRelleno.setPreviewPanel(new JPanel());
		dialogoColorRelleno = JColorChooser.createDialog(botonColorRelleno, "Elige color del relleno", true, selectorColorRelleno, this, null);
        suavizar = new JCheckBox("", configuracion.isSuavizado());
        suavizar.addActionListener(this);
        suavizar.setActionCommand(ConstantesAcciones.SUAVIZAR);
        ajusteSuavidad = new JSpinner(new SpinnerNumberModel(configuracion.getAjusteSuavidad(), 0, 9, 1));
        ajusteSuavidad.setName(ConstantesAcciones.SUAVIDAD);
        ajusteSuavidad.setEnabled(configuracion.isSuavizado());

		
		intervalo = new JSpinner(new SpinnerNumberModel(1000, 50, 1000, 50));
        DefaultEditor editor = (DefaultEditor) intervalo.getEditor();
        editor.getTextField().setColumns(4);

		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Intervalo", intervalo, "Leyenda", mostrarLeyenda, BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Pendientes", mostrarPendiente, "Altitudes", mostrarAltitud, BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Color Línea", botonColorLinea, "Color Relleno", botonColorRelleno, BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Ver Cero", ejeCero, "Relleno", relleno, BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Suavizar", suavizar, "ajuste", ajusteSuavidad, BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Líneas principales", mostrarLineasPrincipales, "Líneas secundarias", mostrarLineasSecundarias, BorderFactory.createEmptyBorder()));
		
		JPanel botones = new JPanel();
		botones.setBounds(0, 340, 380, 40);
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
		intervalo.setValue(configuracion.getIntervalo());
		mostrarLineasPrincipales.setSelected(configuracion.isMostrarLineasPrincipales());
		mostrarLineasSecundarias.setSelected(configuracion.isMostrarLineasSecundarias());
		mostrarPendiente.setSelected(configuracion.isMostrarPendiente());
		mostrarAltitud.setSelected(configuracion.isMostrarAltitud());
		mostrarLeyenda.setSelected(configuracion.isMostrarLeyenda());
		ajusteSuavidad.setValue(configuracion.getAjusteSuavidad());
		suavizar.setSelected(configuracion.isSuavizado());
		ejeCero.setSelected(configuracion.isEjeCero());
		relleno.setSelected(configuracion.isRelleno());
		
		colorLinea = configuracion.getColorLinea();
        botonColorLinea.setBackground(colorLinea);
        selectorColorLinea.setColor(colorLinea);
		colorRelleno = configuracion.getColorRelleno();
        botonColorRelleno.setBackground(colorRelleno);
        selectorColorRelleno.setColor(colorRelleno);
	}
	
	public void actionPerformed(ActionEvent evento) {
    	if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand()))  {
			configuracion.setIntervalo((Integer) intervalo.getValue());
			configuracion.setColorLinea(colorLinea);
			configuracion.setColorRelleno(colorRelleno);
			configuracion.setAjusteSuavidad((Integer) ajusteSuavidad.getValue());
			configuracion.setSuavizado(suavizar.isSelected());
			configuracion.setRelleno(relleno.isSelected());
			configuracion.setEjeCero(ejeCero.isSelected());
			configuracion.setMostrarLineasSecundarias(mostrarLineasSecundarias.isSelected());
			configuracion.setMostrarLineasPrincipales(mostrarLineasPrincipales.isSelected());
			configuracion.setMostrarPendiente(mostrarPendiente.isSelected());
			configuracion.setMostrarAltitud(mostrarAltitud.isSelected());
			configuracion.setMostrarLeyenda(mostrarLeyenda.isSelected());
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_CONFIGURACION_COEFICIENTES));
			setVisible(false);
		}
    	if (ConstantesAcciones.CANCELAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		update();
    		setVisible(false);
    	}
		if (ConstantesAcciones.SUAVIZAR.equalsIgnoreCase(evento.getActionCommand())) {
			ajusteSuavidad.setEnabled(suavizar.isSelected());
		}	    	
		if (ConstantesAcciones.OK.equalsIgnoreCase(evento.getActionCommand())) {
			this.colorLinea = selectorColorLinea.getColor();
			botonColorLinea.setBackground(colorLinea);
			this.colorRelleno = selectorColorRelleno.getColor();
			botonColorRelleno.setBackground(colorRelleno);
		}	
	}
	
	public void mouseClicked(MouseEvent evento) {
		if (evento.getSource() instanceof JPanel)  {
			JPanel source = (JPanel) evento.getSource();
			if (ConstantesAcciones.COLOR_LINEA.equalsIgnoreCase(source.getName())) dialogoColorLinea.setVisible(true);
			if (ConstantesAcciones.COLOR_RELLENO.equalsIgnoreCase(source.getName())) dialogoColorRelleno.setVisible(true);
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

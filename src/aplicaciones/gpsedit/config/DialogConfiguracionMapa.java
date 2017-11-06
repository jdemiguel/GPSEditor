package aplicaciones.gpsedit.config;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jxmapviewer.VirtualEarthTileFactoryInfo;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class DialogConfiguracionMapa extends JDialog implements ActionListener, MouseListener{

	private static final long serialVersionUID = -3767182358091005886L;
	private ActionListener listener;
	private ConfiguracionMapa configuracion;
	
	private JDialog dialogoColorRuta;
	private JPanel botonColorRuta;
	private Color colorRuta;
	private JColorChooser selectorColorRuta;
	private JDialog dialogoColorSegmento;
	private JPanel botonColorSegmento;
	private Color colorSegmento;
	private JColorChooser selectorColorSegmento;
	private JCheckBox mostrarPuntos; 
	private JRadioButton osm;
	private JRadioButton virtualEarth;
	private JRadioButton mapa;
	private JRadioButton satelite;
	private JRadioButton hibrido;
	
	public DialogConfiguracionMapa(ActionListener listener) {
		configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionMapa();
		setBackground(Color.WHITE);
		this.listener = listener;
		setTitle("Configuración Mapa");
		setLayout(null);
		setBounds(0, 0, 300, 340);
		setModal(true);
		JPanel panelPrincipal = new JPanel(new GridLayout(2,2));
		panelPrincipal.setBounds(0, 0, 280, 240);
		panelPrincipal.setBackground(UtilidadesSwing.COLOR_FONDO);
		this.colorRuta = configuracion.getColorRuta();
		this.colorSegmento = configuracion.getColorSegmento();
		
		mostrarPuntos = new JCheckBox("", true);
		mostrarPuntos.addActionListener(this);
		mostrarPuntos.setActionCommand(ConstantesAcciones.MOSTRAR_PUNTOS);
		
        botonColorRuta = new JPanel();
        botonColorRuta.setName(ConstantesAcciones.COLOR_RUTA);
        botonColorRuta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonColorRuta.setSize(20, 20);
        botonColorRuta.setBackground(configuracion.getColorRuta());
        botonColorRuta.addMouseListener(this);
		selectorColorRuta = new JColorChooser(configuracion.getColorRuta());
		selectorColorRuta.setPreviewPanel(new JPanel());
		dialogoColorRuta = JColorChooser.createDialog(botonColorRuta, "Elige color de la ruta", true, selectorColorRuta, this, null);

        botonColorSegmento = new JPanel();
        botonColorSegmento.setName(ConstantesAcciones.COLOR_SEGMENTO);
        botonColorSegmento.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonColorSegmento.setSize(20, 20);
        botonColorSegmento.setBackground(configuracion.getColorSegmento());
        botonColorSegmento.addMouseListener(this);
		selectorColorSegmento = new JColorChooser(configuracion.getColorSegmento());
		selectorColorSegmento.setPreviewPanel(new JPanel());
		dialogoColorSegmento = JColorChooser.createDialog(botonColorSegmento, "Elige color del segmento seleccionado", true, selectorColorSegmento, this, null);

		osm = new JRadioButton("", false);
		osm.setActionCommand(ConstantesAcciones.CAMBIO_PROVEEDOR);
		osm.addActionListener(this);
		virtualEarth = new JRadioButton("", false);
		virtualEarth.setActionCommand(ConstantesAcciones.CAMBIO_PROVEEDOR);
		virtualEarth.addActionListener(this);
		mapa = new JRadioButton("", false);
		satelite = new JRadioButton("", false);
		hibrido = new JRadioButton("", false);
		ButtonGroup variableOsm = new ButtonGroup();
		variableOsm.add(osm);
		variableOsm.add(virtualEarth);
		ButtonGroup variableCapas = new ButtonGroup();
		variableCapas.add(mapa);
		variableCapas.add(satelite);
		variableCapas.add(hibrido);
		
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Color Ruta", botonColorRuta, "Color Segmento", botonColorSegmento, BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("Mostrar puntos", mostrarPuntos, "", new JLabel(), BorderFactory.createEmptyBorder()));
		panelPrincipal.add(UtilidadesSwing.createBloque2x1("OpenStreetMap", osm, "VirtualEarth", virtualEarth, BorderFactory.createTitledBorder("Proveedor Mapa")));
		panelPrincipal.add(UtilidadesSwing.createTitledBloque3x1(
				"Capas", 
				UtilidadesSwing.createBloque1x2("Mapa", mapa), 
				UtilidadesSwing.createBloque1x2("Satelite", satelite), 
				UtilidadesSwing.createBloque1x2("Híbrido", hibrido)
				));
		
		
		JPanel botones = new JPanel();
		botones.setBounds(0, 240, 280, 40);
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
        mostrarPuntos.setSelected(configuracion.isMostrarPuntos());

        mapa.setSelected(configuracion.getModoMapa().equals(VirtualEarthTileFactoryInfo.MAP));
        satelite.setSelected(configuracion.getModoMapa().equals(VirtualEarthTileFactoryInfo.SATELLITE));
        hibrido.setSelected(configuracion.getModoMapa().equals(VirtualEarthTileFactoryInfo.HYBRID));
        osm.setSelected(configuracion.isOsm());
        
        satelite.setEnabled(!configuracion.isOsm());
        hibrido.setEnabled(!configuracion.isOsm());
        
		colorRuta = configuracion.getColorRuta();
        botonColorRuta.setBackground(colorRuta);
        selectorColorRuta.setColor(colorRuta);
		colorSegmento = configuracion.getColorSegmento();
        botonColorSegmento.setBackground(colorSegmento);
        selectorColorSegmento.setColor(colorSegmento);
	}
	
	public void actionPerformed(ActionEvent evento) {
    	if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand()))  {
			configuracion.setColorSegmento(colorSegmento);
			configuracion.setColorRuta(colorRuta);
			configuracion.setMostrarPuntos(mostrarPuntos.isSelected());
			configuracion.setOsm(osm.isSelected());
	        if (configuracion.isOsm()) {
	        	configuracion.setModoMapa(VirtualEarthTileFactoryInfo.MAP);
	        } else {
				if (mapa.isSelected()) configuracion.setModoMapa(VirtualEarthTileFactoryInfo.MAP);
				else if (satelite.isSelected()) configuracion.setModoMapa(VirtualEarthTileFactoryInfo.SATELLITE);
				else configuracion.setModoMapa(VirtualEarthTileFactoryInfo.HYBRID);
	        }
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_CONFIGURACION_MAPA));
			setVisible(false);
		}
    	if (ConstantesAcciones.CANCELAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		update();
    		setVisible(false);
    	}
		if (ConstantesAcciones.OK.equalsIgnoreCase(evento.getActionCommand())) {
			this.colorSegmento = selectorColorSegmento.getColor();
			botonColorSegmento.setBackground(colorSegmento);
			this.colorRuta = selectorColorRuta.getColor();
			botonColorRuta.setBackground(colorRuta);
		}	
		if (ConstantesAcciones.CAMBIO_PROVEEDOR.equalsIgnoreCase(evento.getActionCommand())) {
	        satelite.setEnabled(virtualEarth.isSelected());
	        hibrido.setEnabled(virtualEarth.isSelected());
	        if (osm.isSelected()) mapa.setSelected(true);
		}	
	}

	public void mouseClicked(MouseEvent evento) {
		if (evento.getSource() instanceof JPanel)  {
			JPanel source = (JPanel) evento.getSource();
			if (ConstantesAcciones.COLOR_RUTA.equalsIgnoreCase(source.getName())) dialogoColorRuta.setVisible(true);
			if (ConstantesAcciones.COLOR_SEGMENTO.equalsIgnoreCase(source.getName())) dialogoColorSegmento.setVisible(true);
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

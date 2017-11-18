package aplicaciones.gpsedit.actividad;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.config.Configuracion;
import aplicaciones.gpsedit.config.ConfiguracionGraficas;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelSelectorPunto extends JPanel implements ActionListener{

	private static final long serialVersionUID = 949766848607918353L;
	private final static String DOWN = "DOWN";
	private final static String UP = "UP";
	private final static String START = "START";
	private final static String END = "END";
	private int punto;
	private JButton inicio;
	private JButton up;
	private JButton down;
	private JButton fin;
	private JLabel valorLabel;
	private int min = 0;
	private int max = 10000;
	private ActionListener listener;
	private String actionName;
	private JLabel titulo;
	private ConfiguracionGraficas configuracion;
	
	PanelSelectorPunto(String titulo, String actionName, ActionListener listener)  {
		this.configuracion = Configuracion.getInstance().getConfiguracionActividad().getConfiguracionGraficas();
		this.setBackground(Color.WHITE);
		this.actionName = actionName;
		this.listener = listener;
		this.titulo = new JLabel(titulo);
		punto = 0;
		setLayout(new FlowLayout());
		inicio = new JButton(UtilidadesSwing.getIcono("resources/icons8-skip-to-start-18.png", 18, 18));
		inicio.setActionCommand(PanelSelectorPunto.START);
		inicio.setBorder(BorderFactory.createEmptyBorder());
		inicio.setBackground(Color.WHITE);
		inicio.addActionListener(this);
		
		down = new JButton(UtilidadesSwing.getIcono("resources/icons8-rewind-18.png", 18, 18));
		down.setActionCommand(PanelSelectorPunto.DOWN);
		down.setBorder(BorderFactory.createEmptyBorder());
		down.setBackground(Color.WHITE);
		down.addActionListener(this);
		
		up = new JButton(UtilidadesSwing.getIcono("resources/icons8-fast-forward-18.png", 18, 18));

		up.setActionCommand(PanelSelectorPunto.UP);
		up.addActionListener(this);
		up.setBorder(BorderFactory.createEmptyBorder());
		up.setBackground(Color.WHITE);
		
		fin = new JButton(UtilidadesSwing.getIcono("resources/icons8-end-18.png", 18, 18));
		fin.setActionCommand(PanelSelectorPunto.END);
		fin.addActionListener(this);
		fin.setBorder(BorderFactory.createEmptyBorder());
		fin.setBackground(Color.WHITE);
		valorLabel = new JLabel("");
		add(inicio);
		add(down);
		add(this.titulo);
		add(valorLabel);
		add(up);
		add(fin);
	}

	public void setMin(int min) {
		if (this.punto < min) {
			GPSEdit.logger.debug("Ojo. Nuevo límite por encima del punto. Actual punto:" + this.punto + ". Actual minimo:" + this.min + ". Nuevo minimo:" + min);
			this.punto = min;
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionName));
		}
		this.min = min;
		update();
	}

	public void setMax(int max) {
		if (this.punto > max) {
			GPSEdit.logger.debug("Ojo. Nuevo límite por debajo del punto. Actual punto:" + this.punto + ". Actual maximo:" + this.max + ". Nuevo maximo:" + max);
			this.punto = max;
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionName));
		}
		this.max = max;
		update();
	}

	public void setPunto(int punto) {
		this.punto = punto;
		update();
	}

	public void setValor(String valor) {
		this.valorLabel.setText(valor);
		if(valor.isEmpty()) this.titulo.setVisible(false);
	}
	
	public boolean isUp() {
		return (punto + configuracion.getIncrementoRango()) <= max;	
	}
	
	public boolean isDown() {
		return (punto - configuracion.getIncrementoRango()) >= min;	
	}
	
	public void up() {
		if (isUp()) setPunto (this.punto + configuracion.getIncrementoRango());
	}
	
	public void down() {
		if (isDown()) setPunto (this.punto - configuracion.getIncrementoRango());
	}
	
	public void end() {
		if (isUp()) setPunto (max);
	}
	
	public void start() {
		if (isDown()) setPunto (min);
	}
	
	public void update() {
		 up.setEnabled(isUp());
		 down.setEnabled(isDown());
		 fin.setEnabled(isUp());
		 inicio.setEnabled(isDown());
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (UP.equalsIgnoreCase(evento.getActionCommand())) up();
		if (DOWN.equalsIgnoreCase(evento.getActionCommand())) down();
		if (START.equalsIgnoreCase(evento.getActionCommand())) start();
		if (END.equalsIgnoreCase(evento.getActionCommand())) end();
		listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionName));
	}

	public int getPunto() {
		return punto;
	}



}

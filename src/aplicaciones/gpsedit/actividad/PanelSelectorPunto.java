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

public class PanelSelectorPunto extends JPanel implements ActionListener{

	private static final long serialVersionUID = 949766848607918353L;
	private final static String DOWN = "DOWN";
	private final static String UP = "UP";
	private int punto;
	private JButton up;
	private JButton down;
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
		down = new JButton(" ◄ ");
		down.setActionCommand(PanelSelectorPunto.DOWN);
		down.setBorder(BorderFactory.createEmptyBorder());
		down.setBackground(Color.WHITE);
		down.addActionListener(this);
		up = new JButton(" ► ");
		up.setActionCommand(PanelSelectorPunto.UP);
		up.addActionListener(this);
		up.setBorder(BorderFactory.createEmptyBorder());
		up.setBackground(Color.WHITE);
		valorLabel = new JLabel("");
		add(down);
		add(this.titulo);
		add(valorLabel);
		add(up);
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
	
	public void update() {
		 up.setEnabled(isUp());
		 down.setEnabled(isDown());
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (UP.equalsIgnoreCase(evento.getActionCommand())) up();
		if (DOWN.equalsIgnoreCase(evento.getActionCommand())) down();
		listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionName));
	}

	public int getPunto() {
		return punto;
	}



}

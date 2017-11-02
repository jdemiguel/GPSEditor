package aplicaciones.gpsedit.config;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.apache.log4j.Logger;


public class PanelConfiguracion extends JPanel {

	private static final long serialVersionUID = -1610363351952876544L;
	private Logger logger = Logger.getLogger("coeficientes");
	private ActionListener actionListenerActividad;

	public PanelConfiguracion(ActionListener actionListenerActividad)  {
		setBounds(0,0,1300,768);
		setLayout(new GridLayout(2,2));		
		this.actionListenerActividad = actionListenerActividad;
	}
	
}

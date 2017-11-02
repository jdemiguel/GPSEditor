package aplicaciones.gpsedit.actividad;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelToolbarMapa extends JPanel{

	//JButton
	
	public PanelToolbarMapa()  {
		setBackground(new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 127));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
}

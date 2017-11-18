package aplicaciones.gpsedit.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class UtilidadesSwing {
	
	public static Color COLOR_FONDO = Color.WHITE;
	public static Color COLOR_TEXTO = Color.BLACK;

	static {
		UIManager.put("Button.disabledText", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("CheckBox.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("RadioButton.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Button.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Label.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Panel.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Slider.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Dialog.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Frame.background", UtilidadesSwing.COLOR_FONDO);
		UIManager.put("Window.background", UtilidadesSwing.COLOR_FONDO);
/*		
		UIDefaults d = UIManager.getDefaults();
		Enumeration<Object> claves = d.keys();
		while (claves.hasMoreElements())
		   System.out.println(claves.nextElement());
		   */
		
		}
	
	public static JPanel createBloque2x1(String textoArriba, JComponent arriba, String textoAbajo, JComponent abajo, Border borde) {
		JPanel bloque  =  new JPanel(new GridLayout(2, 1));
	    bloque.setBackground(UtilidadesSwing.COLOR_FONDO);
        bloque.setBorder(borde);
        bloque.add(UtilidadesSwing.createBloque1x2(textoArriba, arriba));
        bloque.add(UtilidadesSwing.createBloque1x2(textoAbajo, abajo));
        return bloque;
	}

	public static JPanel createBloque1x2 (String texto, JComponent componente)  {
		JPanel bloque = new JPanel(new FlowLayout());
		bloque.add(new JLabel(texto));
		bloque.add(componente);
		return bloque;
	}
	
	public static JPanel createTitledBloque3x1(String titulo, JComponent arriba, JComponent medio, JComponent abajo) {
	    JPanel bloque = new JPanel();
	    bloque.setToolTipText(titulo);
	    bloque.setLayout(new GridLayout(3,1));
	    bloque.setBorder(BorderFactory.createTitledBorder(titulo));
	    bloque.add(arriba);
	    bloque.add(medio);
	    bloque.add(abajo);	
	    return bloque;
	}
	
	public static JPanel createBloque3x1(JComponent arriba, JComponent medio, JComponent abajo) {
	    JPanel bloque = new JPanel();
	    bloque.setLayout(new GridLayout(3,1));
	    
	    bloque.add(arriba);
	    bloque.add(medio);
	    bloque.add(abajo);	
	    return bloque;
	}	
	
	public static JPanel createBloque1x2(JComponent arriba, JComponent abajo) {
	    JPanel bloque = new JPanel();
	    bloque.setLayout(new GridLayout(1,2));
	    
	    bloque.add(arriba);
	    bloque.add(abajo);	
	    return bloque;
	}	
	
	public static JPanel createBloque1x3(JComponent arriba, JComponent medio, JComponent abajo) {
	    JPanel bloque = new JPanel();
	    bloque.setLayout(new GridLayout(1,3));
	    
	    bloque.add(arriba);
	    bloque.add(medio);
	    bloque.add(abajo);	
	    return bloque;
	}	
	
	public static ImageIcon getIcono(String nombreFichero, int w, int h)  {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    try {
		    Graphics2D g2 = resizedImg.createGraphics();
		    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(ImageIO.read(ClassLoader.getSystemResource( nombreFichero )), 0, 0, w, h, null);
		    g2.dispose();
		} catch (Exception e) {}
	    return new ImageIcon(resizedImg);
	}

	public static Stroke getStrokeDiscontinuo()  {
		float dash1[] = {10.0f};
		return new BasicStroke(.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	}


	
}

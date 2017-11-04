package aplicaciones.gpsedit.menus;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.actividad.DialogProgreso;
import aplicaciones.gpsedit.actividad.PanelActividad;

public class EditMenu extends JMenu{
	
	private static final long serialVersionUID = 716310743424732893L;

	public EditMenu(PanelActividad panelActividad) {
		DialogProgreso dialogProgreso = panelActividad.getDialogoProgreso();
		setText("Edición");
		JMenuItem jmenuItem = new JMenuItem("Deshacer");
		jmenuItem.setActionCommand(ConstantesAcciones.DESHACER);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		jmenuItem.addActionListener(dialogProgreso);
		add(jmenuItem);	
		jmenuItem = new JMenuItem("Rehacer");
		jmenuItem.setActionCommand(ConstantesAcciones.REHACER);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		jmenuItem.addActionListener(dialogProgreso);
		add(jmenuItem);	
		addSeparator();
		JMenu menuBorrado = new JMenu("Borrar");
		jmenuItem = new JMenuItem("Todos los puntos seleccionados");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_ALL);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de altitud");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_ALTITUD);
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de cadencia");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_CADENCIA);
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de GPS");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_GPS);
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de pulsaciones");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_HR);
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de potencia");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_POTENCIA);
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de velocidad");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_VELOCIDAD);
		jmenuItem.addActionListener(dialogProgreso);
		menuBorrado.add(jmenuItem);
		add (menuBorrado);
		JMenu menuSet = new JMenu("Establecer valor constante");
		jmenuItem = new JMenuItem("Datos de altitud");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_ALTITUD);
		jmenuItem.addActionListener(dialogProgreso);
		menuSet.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de cadencia");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_CADENCIA);
		jmenuItem.addActionListener(dialogProgreso);
		menuSet.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de pulsaciones");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_HR);
		jmenuItem.addActionListener(dialogProgreso);
		menuSet.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de potencia");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_POTENCIA);
		jmenuItem.addActionListener(dialogProgreso);
		menuSet.add(jmenuItem);
		jmenuItem = new JMenuItem("Datos de velocidad");
		jmenuItem.setActionCommand(ConstantesAcciones.SET_VELOCIDAD);
		jmenuItem.addActionListener(dialogProgreso);
		menuSet.add(jmenuItem);
		add (menuSet);		
		addSeparator();
		jmenuItem = new JMenuItem("Quitar paradas");
		jmenuItem.setActionCommand(ConstantesAcciones.DELETE_PARADAS);
		jmenuItem.addActionListener(dialogProgreso);
		add(jmenuItem);		
		
		
	}

}

package aplicaciones.gpsedit;


import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import aplicaciones.gpsedit.actividad.DatosActividad;
import aplicaciones.gpsedit.actividad.PanelActividad;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.excepciones.ExtensionInvalidaException;
import aplicaciones.gpsedit.gps.GPSParser;
import aplicaciones.gpsedit.gps.GPSParserFactory;
import aplicaciones.gpsedit.gps.TrackUtil;
import aplicaciones.gpsedit.menus.EditMenu;
import aplicaciones.gpsedit.util.FileFilter;
import aplicaciones.gpsedit.util.UtilidadesFicheros;


/**
 *
 * @author	Jesus
 * @version	
 */
public class GPSEdit extends javax.swing.JFrame implements Runnable, ActionListener  {

    private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("GPSEdit");
	
	private	Thread thread;
	static private GPSEdit instance = new GPSEdit();
	public PanelActividad panelActividad;


	private DatosActividad datosActividad;
	
	public static void main(String args[]) {
		GPSEdit	window = GPSEdit.getInstance();
		window.setTitle("GPSImport");
		window.setVisible(true);
		window.addWindowListener(new java.awt.event.WindowAdapter(){
        		public void windowClosing(WindowEvent e){
        		System.exit(0);
        		}
        	}
        	);

	}
	public static GPSEdit getInstance()  {
		return instance;
	}
	private GPSEdit() {
		this.thread	=	new	Thread(	this );
		getContentPane().setLayout(null);
		setBounds(0,0,1350,850);
		crearComponentes();	


		this.thread.start();
		enableEvents(AWTEvent.KEY_EVENT_MASK);
		//File fichero = new File("d:\\tmp\\cycling\\2017-01-22 840650415 Race cycling Strsava.tcx");
		try {
			//abrirFichero(fichero, UtilidadesFicheros.getTipo(fichero));
		} catch (Exception e) {
		}

	}

	private	void crearComponentes()	 {
	
		panelActividad = new PanelActividad();
		panelActividad.setVisible(false);

		getContentPane().add(panelActividad);
		//creamos los menus
		JMenuBar	menuBar	=	new	JMenuBar();
		JMenuItem jmenuItem;
		JMenu menu;
		JMenu submenu;

		
		menu = new JMenu("Archivo");
		
		jmenuItem = new JMenuItem("Abrir");
		jmenuItem.setActionCommand(ConstantesAcciones.MENU_ABRIR);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		jmenuItem.addActionListener(this);
		menu.add(jmenuItem);
		jmenuItem = new JMenuItem("Guardar");
		jmenuItem.setActionCommand(ConstantesAcciones.MENU_GUARDAR);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		jmenuItem.addActionListener(this);
		menu.add(jmenuItem);
		jmenuItem = new JMenuItem("Guardar como");
		jmenuItem.setActionCommand(ConstantesAcciones.MENU_GUARDAR_COMO);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		jmenuItem.addActionListener(this);
		menu.add(jmenuItem);
		jmenuItem = new JMenuItem("Cerrar");
		jmenuItem.setActionCommand(ConstantesAcciones.MENU_CERRAR);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		jmenuItem.addActionListener(this);
		menu.add(jmenuItem);
		menu.addSeparator();
		jmenuItem = new JMenuItem("Salir");
		jmenuItem.setActionCommand(ConstantesAcciones.MENU_SALIR);
		jmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		jmenuItem.addActionListener(this);
		menu.add(jmenuItem);
		menuBar.add(menu);

		menuBar.add(new EditMenu(panelActividad));		

		menu = new JMenu("Config");

		submenu = new JMenu("Actividad");

		jmenuItem = new JMenuItem("Gráficas");
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_GRAFICAS);
		jmenuItem.addActionListener(panelActividad);
		submenu.add(jmenuItem);

		jmenuItem = new JMenuItem("Secciones");
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_SECCIONES);
		jmenuItem.addActionListener(panelActividad);
		submenu.add(jmenuItem);
		
		jmenuItem = new JMenuItem("Mapa");
		jmenuItem.setActionCommand(ConstantesAcciones.CAMBIAR_CONFIGURACION_MAPA);
		jmenuItem.addActionListener(panelActividad);
		submenu.add(jmenuItem);
		
		menu.add(submenu);
		menuBar.add(menu);


		setJMenuBar(menuBar);
	}

	public void salir()  {
		System.exit(0);
	}
	
	public void abrir()  {
		logger.debug("abriendo");
		try {
		    JFileChooser chooser = new JFileChooser("C:\\Users\\jdemgon\\Downloads");
		    FileFilter filtro = new FileFilter();
		    filtro.addExtension("gpx");
		    filtro.addExtension("tcx");
		    filtro.setDescription("ficheros GPS");
		    chooser.setFileFilter(filtro);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
				logger.debug("abierto el fichero:" + chooser.getSelectedFile().getAbsolutePath());
		    	String tipo = UtilidadesFicheros.getTipo(chooser.getSelectedFile());
				logger.debug("abierto el fichero con tipo:" + tipo);
				abrirFichero(chooser.getSelectedFile(), tipo);
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} finally  {
		}
	}
	
	public void abrirFichero(File fichero, String tipo) {
		try {
			panelActividad.setVisible(false);
			GPSParser parser = GPSParserFactory.getParser(tipo);
			Track track = TrackUtil.toTrack(parser.parse(fichero));
			datosActividad = new DatosActividad(track);
			if (datosActividad.getTrack().getTipoActividad() == null) datosActividad.setTipoActividad();
	        panelActividad.update(datosActividad);
			panelActividad.setVisible(true);
		} catch (Exception e) {
			logger.error("Error al abrir el fichero", e);
			JOptionPane.showMessageDialog(null, "No se puede abrir el fichero");
		}
	}

	public void cerrar()  {
		panelActividad.setVisible(false);
	}
	
	
	public void guardarComo()  {
		File fichero = null;
		try {
		    JFileChooser chooser = new JFileChooser();
		    chooser.setSelectedFile(datosActividad.getTrack().getFichero());
		    FileFilter filtro = new FileFilter();
		    filtro.addExtension("gpx");
		    filtro.addExtension("tcx");
		    filtro.setDescription("Ficheros GPS (gpx, tcx)");
		    chooser.setFileFilter(filtro);
		    int returnVal = chooser.showSaveDialog(this);

		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	fichero = chooser.getSelectedFile();
		    	String tipo = UtilidadesFicheros.getTipo(fichero);
				guardar(fichero, tipo);
		    }
		} catch (ExtensionInvalidaException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void guardar()  {
		guardar(datosActividad.getTrack().getFichero(), datosActividad.getTrack().getTipo());
	}
	
	/**
	 * Comprueba si existe y no se ha guardado aún y en ese caso pregunta al usuario si está seguro 
	 * @return
	 */
	private boolean checkExiste (File fichero) {
		if (!fichero.exists() || datosActividad.getTrack().isGrabado()) return true;
		return (JOptionPane.showConfirmDialog(null, "El fichero existe. ¿Quiere sobreescribirlo?") == JOptionPane.OK_OPTION);
	}
	
	private void guardar(File fichero, String tipo)  {
		//si el fichero a grabar es diferente se deshabilita la opcion grabado (esta opcion evita pedir al usuario confirmaciones repetitivas de que quiere sobreescribir, solo se pregunta una vez
		if (!fichero.getAbsolutePath().equalsIgnoreCase(datosActividad.getTrack().getFichero().getAbsolutePath())) datosActividad.getTrack().setGrabado(false); 
		if (checkExiste(fichero))  {
			try {
				Document document = GPSParserFactory.getParser(tipo).format(TrackUtil.toRawTrack(datosActividad.getTrack()));
//				logger.debug(UtilXML.xmlToString(document));
				UtilidadesFicheros.xmlToFile(document, fichero);

				if (!datosActividad.getTrack().isGrabado()) JOptionPane.showMessageDialog(null, "El fichero se ha guardado correctamente");
				datosActividad.getTrack().setTipo(tipo);
				datosActividad.getTrack().setFichero(fichero);
				datosActividad.getTrack().setGrabado(true);
			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "No se puede guardar el fichero");
				logger.error("Error al guardar el fichero", e);
				e.printStackTrace();
			}
		}

	}

	public void actionPerformed(ActionEvent evento) {
	 	logger.debug("actionPerformed(" +evento.getActionCommand()+")");
	 	if (evento.getActionCommand().equals(ConstantesAcciones.MENU_SALIR)) salir();
	 	if (evento.getActionCommand().equals(ConstantesAcciones.MENU_ABRIR)) abrir();
	 	if (evento.getActionCommand().equals(ConstantesAcciones.MENU_CERRAR)) cerrar();
	 	if (evento.getActionCommand().equals(ConstantesAcciones.MENU_GUARDAR)) guardar();	
	 	if (evento.getActionCommand().equals(ConstantesAcciones.MENU_GUARDAR_COMO)) guardarComo();	
	}
	
 	
	public void	run()	{

//aqui metemos todo	lo que se	ejecute	periodicamente (control	de interrupciones);
		while (true)  {
			try	 {
				Thread.sleep(1000);
			}	catch( Exception e ) {
					e.printStackTrace();
					logger.error("run():" + e.toString());
			}
		}
	}
	

	
	public void processKeyEvent(KeyEvent evento)  {
		if (evento.getID() == KeyEvent.KEY_RELEASED)  {
			int codigo = evento.getKeyCode();

		}
	}


   
}


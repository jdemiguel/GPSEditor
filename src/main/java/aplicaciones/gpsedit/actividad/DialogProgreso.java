package aplicaciones.gpsedit.actividad;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DialogProgreso extends JDialog implements PropertyChangeListener, ActionListener{

	private DatosActividad datosActividad;
	private ActionListener listener;
	private static final long serialVersionUID = -6327061299249700056L;
	private JProgressBar progressBar;
	
	public DialogProgreso(ActionListener listener) {
		this.listener = listener;
		setBounds(0, 0, 270, 80);
		JPanel panel = new JPanel(new GridLayout(2, 1));
		getContentPane().add(panel);
		panel.setBounds(10, 10, 250, 60);
		panel.add(new JLabel("Procesando"));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		panel.add(progressBar);
		progressBar.setStringPainted(true);		
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            if(progress >= 1 && progress < 100) setVisible(true);
            else setVisible(false);
        } 		
	}
	
	public void actionPerformed(ActionEvent evento) {
		TareasActividad tarea = new TareasActividad(evento.getActionCommand(), datosActividad, listener);
		tarea.addPropertyChangeListener(this);
		tarea.execute();
	}

	public void setDatosActividad(DatosActividad datosActividad) {
		this.datosActividad = datosActividad;
	}

}

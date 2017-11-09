package aplicaciones.gpsedit.actividad;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.util.UtilidadesSwing;
import lu.tudor.santec.jtimechooser.JTimeChooser;

public class DialogSelectorFecha extends JDialog implements ActionListener{

	private static final long serialVersionUID = 7539948379642608644L;
	private DatosActividad datosActividad;
	private JDateChooser dateChooser;

	
	public DialogSelectorFecha(DatosActividad datosActividad) {
		setBackground(Color.WHITE);
		this.datosActividad = datosActividad;
		setTitle("Selector de fecha");
		setLayout(null);
		setBounds(0, 0, 300, 340);
		setModal(true);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBounds(0, 0, 280, 240);
		panelPrincipal.setBackground(UtilidadesSwing.COLOR_FONDO);

		dateChooser = new JDateChooser(null, datosActividad.getTrack().getPrimero().getHora(), "dd/MM/yyyy", new JSpinnerDateEditor());
		panelPrincipal.add(dateChooser);
		JTimeChooser timechooser = new JTimeChooser(datosActividad.getTrack().getPrimero().getHora());
		panelPrincipal.add(timechooser);
		
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
	

	
	public void actionPerformed(ActionEvent evento) {
    	if (ConstantesAcciones.ACEPTAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		GPSEdit.logger.debug("Fecha1:" + dateChooser.getDate().toString());
    		GPSEdit.logger.debug("Fecha2:" + dateChooser.getDateEditor().getDate().toString());
    		GPSEdit.logger.debug("Fecha3:" + dateChooser.getJCalendar().getDate().toString());

    		datosActividad.setFecha(dateChooser.getJCalendar().getDate());
			setVisible(false);
		}
    	if (ConstantesAcciones.CANCELAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		setVisible(false);
    	}
	}

	
}

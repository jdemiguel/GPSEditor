package aplicaciones.gpsedit.actividad;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class DialogSelectorFecha extends JDialog implements ActionListener{

	private static final long serialVersionUID = 7539948379642608644L;
	private DatosActividad datosActividad;
	private JSpinner hora;

	
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

		SpinnerDateModel smb = new SpinnerDateModel(datosActividad.getTrack().getPrimero().getHora(), null, null, Calendar.MINUTE);
		hora = new JSpinner(smb);		
		
		JSpinner.DateEditor d = new JSpinner.DateEditor(hora, "dd/MM/yyyy HH:mm:ss");
		hora.setEditor(d);
		
		panelPrincipal.add(hora);
		
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
    		GPSEdit.logger.debug("Fecha1:" + (Date) hora.getValue());

    		datosActividad.setFecha((Date) hora.getValue());
			setVisible(false);
		}
    	if (ConstantesAcciones.CANCELAR.equalsIgnoreCase(evento.getActionCommand()))  {
    		setVisible(false);
    	}
	}

	
}

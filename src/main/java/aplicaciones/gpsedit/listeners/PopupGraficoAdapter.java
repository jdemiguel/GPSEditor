package aplicaciones.gpsedit.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.actividad.DatosActividad;

public class PopupGraficoAdapter implements ActionListener, PopupMenuListener{

	private int puntoSeleccionadoPopup;

	private DatosActividad datosActividad;
	private ActionListener listener;

	public PopupGraficoAdapter(ActionListener listener) {
		this.listener = listener;
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.SET_INICIO_RANGO.equals(evento.getActionCommand())) {
			if (puntoSeleccionadoPopup > datosActividad.getInicioRango() && puntoSeleccionadoPopup < datosActividad.getFinRango()) {
				datosActividad.setInicioRango(puntoSeleccionadoPopup);
				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
			}
		}
		if (ConstantesAcciones.SET_FIN_RANGO.equals(evento.getActionCommand())) {
			if (puntoSeleccionadoPopup > datosActividad.getInicioRango() && puntoSeleccionadoPopup < datosActividad.getFinRango()) {
				datosActividad.setFinRango(puntoSeleccionadoPopup);
				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
			}
		}
	}
	public void setDatosActividad(DatosActividad datosActividad) {
		this.datosActividad = datosActividad;
	}

	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		puntoSeleccionadoPopup = datosActividad.getPuntoSeleccionado();
	}
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}
	public void popupMenuCanceled(PopupMenuEvent e) {
	}

}

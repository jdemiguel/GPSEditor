package aplicaciones.gpsedit.actividad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.beans.ActividadBean;
import aplicaciones.gpsedit.beans.RawTrack;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.gps.TrackUtil;
import aplicaciones.gpsedit.util.DeshacerManager;

public class TareasActividad extends SwingWorker<String, Object> {
	
	private String accion;
	private DatosActividad datosActividad;
	private ActionListener listener;

	protected String doInBackground() throws Exception{
		ActividadBean puntoRetorno = datosActividad.getActividadBean().clone();
		try {
			if (ConstantesAcciones.DELETE_ALL.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los puntos seleccionados") == JOptionPane.OK_OPTION) {
		 			try {
		 				setProgress(1);
		 				datosActividad.borraSegmento();
			 			resetTrack(puntoRetorno);
			 			datosActividad.resetRango();
		 			} catch (Exception e) {
		 				setProgress(100);
		 				JOptionPane.showMessageDialog(null, "No se pueden borrar tantos puntos");
		 			}	
		 		}
			}
			if (ConstantesAcciones.DELETE_ALTITUD.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los datos de altitud de los puntos seleccionados?") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraAltitud();
		 			resetTrack(puntoRetorno);
		 		}
			}
			if (ConstantesAcciones.DELETE_CADENCIA.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los datos de cadencia de los puntos seleccionados?") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraCadencia();
		 			resetTrack(puntoRetorno);
		 		}
			}
			if (ConstantesAcciones.DELETE_GPS.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los datos de GPS de los puntos seleccionados?") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraGPS();
		 			resetTrack(puntoRetorno);
		 		}
			}
			if (ConstantesAcciones.DELETE_HR.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los datos de pulsaciones de los puntos seleccionados?") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraHR();
		 			resetTrack(puntoRetorno);
		 		}
			}				
			if (ConstantesAcciones.DELETE_POTENCIA.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los datos de potencia de los puntos seleccionados?") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraPotencia();
		 			resetTrack(puntoRetorno);
		 		}
			}		
			if (ConstantesAcciones.DELETE_VELOCIDAD.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los datos de velocidad de los puntos seleccionados?\n En ese caso los datos de velocidad serán recalculados a partir de los datos GPS si los hay") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraVelocidad();
		 			resetTrack(puntoRetorno);
	
		 		}
			}	
			if (ConstantesAcciones.DELETE_PARADAS.equalsIgnoreCase(accion)) {
		 		if (JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar los tiempos de parada de los puntos seleccionados?") == JOptionPane.OK_OPTION) {
	 				setProgress(1);
		 			datosActividad.borraParadas();
		 			resetTrack(puntoRetorno);
	
		 		}
			}				
			if (ConstantesAcciones.SET_ALTITUD.equalsIgnoreCase(accion)) {
				try {
					String valor = JOptionPane.showInputDialog("Nuevo valor de altitud");
					if (valor != null)  {
						int altitud = Integer.parseInt(valor);
		 				setProgress(1);
						datosActividad.setAltitud(altitud);
						resetTrack(puntoRetorno);
					}
				} catch (NumberFormatException e) {
	 				JOptionPane.showMessageDialog(null, "Valor no válido");
				}
			}		
			if (ConstantesAcciones.SET_CADENCIA.equalsIgnoreCase(accion)) {
				try {
					String valor = JOptionPane.showInputDialog("Nuevo valor de cadencia");
					if (valor != null)  {
						int cadencia = Integer.parseInt(valor);
		 				setProgress(1);
						datosActividad.setCadencia(cadencia);
						resetTrack(puntoRetorno);
					}
				} catch (NumberFormatException e) {
	 				JOptionPane.showMessageDialog(null, "Valor no válido");
				}
			}		
			if (ConstantesAcciones.SET_HR.equalsIgnoreCase(accion)) {
				try {
					String valor = JOptionPane.showInputDialog("Nuevo valor de pulsaciones");
					if (valor != null)  {
						int hr = Integer.parseInt(valor);
		 				setProgress(1);
						datosActividad.setHR(hr);
						resetTrack(puntoRetorno);
					}
				} catch (NumberFormatException e) {
	 				JOptionPane.showMessageDialog(null, "Valor no válido");
				}
			}		
			if (ConstantesAcciones.SET_POTENCIA.equalsIgnoreCase(accion)) {
				try {
					String valor = JOptionPane.showInputDialog("Nuevo valor de potencia");
					if (valor != null)  {
						int potencia = Integer.parseInt(valor);
		 				setProgress(1);
						datosActividad.setPotencia(potencia);
						resetTrack(puntoRetorno);
					}
				} catch (NumberFormatException e) {
	 				JOptionPane.showMessageDialog(null, "Valor no válido");
				}
			}		
			if (ConstantesAcciones.SET_VELOCIDAD.equalsIgnoreCase(accion)) {
				try {
					String valor = JOptionPane.showInputDialog("Nuevo valor de velocidad");
					if (valor != null)  {
						double velocidad = Double.parseDouble(valor);
		 				setProgress(1);
						datosActividad.setVelocidad(velocidad);
						resetTrack(puntoRetorno);
					}
				} catch (NumberFormatException e) {
	 				JOptionPane.showMessageDialog(null, "Valor no válido");
				}
			}		
			if (ConstantesAcciones.DESHACER.equalsIgnoreCase(accion) && DeshacerManager.getInstance().isDeshacer()) {
 				setProgress(50);
 				datosActividad.setActividadBean(DeshacerManager.getInstance().deshacer(puntoRetorno));
 				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_TRACK));		
 				setProgress(100); 				
			}						
			if (ConstantesAcciones.REHACER.equalsIgnoreCase(accion) && DeshacerManager.getInstance().isRehacer()) {
 				setProgress(50);
 				datosActividad.setActividadBean(DeshacerManager.getInstance().rehacer(puntoRetorno));
 				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_TRACK));		
 				setProgress(100); 				
			}
		} catch (Exception e)  {
			GPSEdit.logger.debug("Error editando el track", e);
			setProgress(100);
			JOptionPane.showMessageDialog(null, "No se ha podido realizar el cambio");
		}

		return "";
	}
	
	public TareasActividad(String accion, DatosActividad datosActividad, ActionListener listener) {
		this.accion = accion;
		this.datosActividad = datosActividad;
		this.listener = listener;
	}

	/**
	 * Recalcula todos los valores de la actividad por haber efectuado edición de puntos del track
	 */
	public void resetTrack(ActividadBean puntoRetorno) {
		setProgress(25);
		RawTrack rawTrack = TrackUtil.toRawTrack(datosActividad.getTrack());
		setProgress(50);
		Track track = TrackUtil.toTrack(rawTrack);
		setProgress(75);
		datosActividad.setTrack(track);
		DeshacerManager.getInstance().guardarCambio(puntoRetorno);
		listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_TRACK));		
		setProgress(100);
	}

	
    public void done() {
		GPSEdit.logger.debug("Done");
    }

}

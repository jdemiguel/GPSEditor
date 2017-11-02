package aplicaciones.gpsedit.actividad;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.Track;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelDatosActividad extends JPanel implements ActionListener{

	private static final long serialVersionUID = 8309362132855364925L;
	
	private PanelCampo tiempoAbsolutoLabel = new PanelCampo("Tiempo total:");
	private PanelCampo tiempoMovimientoLabel = new PanelCampo("Tiempo en movimiento:");
	private PanelCampo longitudLabel = new PanelCampo("Longitud total:");
	private PanelCampo desnivelAcumuladoLabel = new PanelCampo("Desnivel acumulado:");
	private PanelCampo coeficienteLabel = new PanelCampo("Coeficiente APM:");
	private PanelCampo velocidadMedLabel = new PanelCampo("Velocidad Media:");
	private PanelCampo pasoMedLabel = new PanelCampo("Paso Medio:");
	private PanelCampo hrMedLabel = new PanelCampo("HR media:");
	private PanelCampo cadenciaMedLabel = new PanelCampo("Cadencia media:");
	private PanelCampo potenciaMedLabel = new PanelCampo("Potencia media:");
	private PanelCampo descripcionLabel = new PanelCampo("Notas:");
	private PanelCampo caloriasLabel = new PanelCampo("Calorías:");
	private PanelCampo tipoActividadLabel = new PanelCampo("Actividad:");
	private PanelCampo dispositivoLabel = new PanelCampo("Dispositivo:");
	private PanelCampo autorLabel = new PanelCampo("Autor:");

	public PanelDatosActividad() {
		setLayout(new GridLayout(3, 5));
	}
	public void update(DatosActividad datosActividad)  {
		removeAll();
		Track track = datosActividad.getTrack();
		DatosSegmentoBean datosTrack = datosActividad.getDatosTrack();
		
		longitudLabel.setValor(UtilidadesFormat.getFloatFormat().format(datosTrack.getLongitud() / 1000.0) + " km");
		tiempoAbsolutoLabel.setValor(UtilidadesFormat.getTiempoFormat().format(datosTrack.getTiempoAbsoluto()) + " horas");
		tiempoMovimientoLabel.setValor(UtilidadesFormat.getTiempoFormat().format(datosTrack.getTiempoMovimiento()) + " horas");
	    add(longitudLabel); 
	    add(tiempoAbsolutoLabel);
	    add(tiempoMovimientoLabel);
		
	    if (track.getTipoActividad().isPaso()) {
			pasoMedLabel.setValor(UtilidadesFormat.getPasoFormat().format(datosTrack.getPasoMed()) + " min/km");        
		    add(pasoMedLabel);
	    } else {
			velocidadMedLabel.setValor(UtilidadesFormat.getDecimalFormat().format(datosTrack.getVelocidadMed()) + " km/h");        
		    add(velocidadMedLabel);
	    }
		
		if (datosTrack.getCadenciaMed() > 0) {
	 	    add(cadenciaMedLabel); 
			cadenciaMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosTrack.getCadenciaMed()));
		}
		
		if (datosTrack.getHrMed() > 0) {
		    add(hrMedLabel);
			hrMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosTrack.getHrMed()) + " bpm");
		}
		
		if (datosTrack.getPotenciaMed() > 0) {
		    add(potenciaMedLabel);
			potenciaMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosTrack.getPotenciaMed()));
		}
		
		if (datosTrack.getAltitudMed() > 0 && track.isHayGPS()) {
		    add(desnivelAcumuladoLabel); 
			desnivelAcumuladoLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosTrack.getDesnivelAcumulado()) + " m");
			add(coeficienteLabel);
			coeficienteLabel.setValor(UtilidadesFormat.getDecimalFormat().format(datosActividad.getCoeficienteAPMTotal()));
		}
		
		if (track.getCalorias() > 0) {
		    add(caloriasLabel);
			caloriasLabel.setValor(UtilidadesFormat.getIntegerFormat().format(track.getCalorias()) + " kcal");   
		}
		if (track.getTipoActividad() != null) {
			tipoActividadLabel.setValor(track.getTipoActividad().getNombre());        
		    add(tipoActividadLabel);
		}
		if (track.getDescripcion() != null) {
			descripcionLabel.setValor(track.getDescripcion());        
		    add(descripcionLabel);
		}
		if (track.getDispositivo() != null) {
			dispositivoLabel.setValor(track.getDispositivo().toString());    
		    add(dispositivoLabel);
		}
		if (track.getAutor() != null) {
			autorLabel.setValor(track.getAutor().toString());    
		    add(autorLabel);
		}
	}

	public void actionPerformed(ActionEvent evento) {
	
	}
}

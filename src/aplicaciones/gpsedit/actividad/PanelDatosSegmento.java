package aplicaciones.gpsedit.actividad;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JPanel;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.util.UtilidadesFormat;
import aplicaciones.gpsedit.util.UtilidadesSwing;

public class PanelDatosSegmento extends JPanel implements ActionListener{

	private static final long serialVersionUID = 8309362132855364925L;
	
	private PanelCampo tiempoAbsolutoLabel = new PanelCampo("Total:");
	private PanelCampo tiempoMovimientoLabel = new PanelCampo("Total:");
	private PanelCampo longitudLabel = new PanelCampo("Total:");
	private PanelCampo desnivelLabel = new PanelCampo("Medio:");
	private PanelCampo desnivelAcumuladoLabel = new PanelCampo("Acum:");
	private PanelCampo coeficienteLabel = new PanelCampo("Coef APM:");
	private PanelCampo altitudMaxLabel = new PanelCampo("Máxima:");
	private PanelCampo altitudMedLabel = new PanelCampo("Media:");
	private PanelCampo altitudMinLabel = new PanelCampo("Mínima:");
	private PanelCampo velocidadMinLabel = new PanelCampo("Mínima:");
	private PanelCampo velocidadMedLabel = new PanelCampo("Media:");
	private PanelCampo velocidadMaxLabel = new PanelCampo("Máxima:");
	private PanelCampo pasoMinLabel = new PanelCampo("Mínimo:");
	private PanelCampo pasoMedLabel = new PanelCampo("Medio:");
	private PanelCampo pasoMaxLabel = new PanelCampo("Máximo:");
	private PanelCampo hrMinLabel = new PanelCampo("Mínima:");
	private PanelCampo hrMedLabel = new PanelCampo("Media:");
	private PanelCampo hrMaxLabel = new PanelCampo("Máxima:");
	private PanelCampo cadenciaMinLabel = new PanelCampo("Mínima:");
	private PanelCampo cadenciaMedLabel = new PanelCampo("Media:");
	private PanelCampo cadenciaMaxLabel = new PanelCampo("Máxima:");
	private PanelCampo potenciaMinLabel = new PanelCampo("Mínima:");
	private PanelCampo potenciaMedLabel = new PanelCampo("Media:");
	private PanelCampo potenciaMaxLabel = new PanelCampo("Máxima:");
	private PanelCampo pendienteMinLabel = new PanelCampo("Mínima:");
	private PanelCampo pendienteMedLabel = new PanelCampo("Media:");
	private PanelCampo pendienteMaxLabel = new PanelCampo("Máxima:");
	
	private JPanel bloqueDistancia;
	private JPanel bloqueTiempoMov;
	private JPanel bloqueHora;
	private JPanel bloqueDesnivel;
	private JPanel bloqueCadencia;
	private JPanel bloqueHR;
	private JPanel bloquePotencia;
	private JPanel bloquePendiente;
	private JPanel bloquePaso;
	private JPanel bloqueVelocidad;
	private JPanel bloqueAltitud;
	private PanelSelectorPunto puntoInicioDistancia;
	private PanelSelectorPunto puntoFinalDistancia;
	private PanelSelectorPunto puntoInicioTiempo;
	private PanelSelectorPunto puntoFinalTiempo;
	private PanelSelectorPunto puntoInicioHora;
	private PanelSelectorPunto puntoFinalHora;
	private ActionListener listener;
	private DatosActividad datosActividad;



	public PanelDatosSegmento(ActionListener listener) {
		this.listener = listener;
		setLayout(new FlowLayout());
	    
	    puntoInicioDistancia = new PanelSelectorPunto("Inicio:", ConstantesAcciones.PUNTO_INICIO, this);
	    puntoFinalDistancia = new PanelSelectorPunto("Final:", ConstantesAcciones.PUNTO_FINAL, this);
	    puntoInicioTiempo = new PanelSelectorPunto("Inicio:", ConstantesAcciones.PUNTO_INICIO, this);
	    puntoFinalTiempo = new PanelSelectorPunto("Final:", ConstantesAcciones.PUNTO_FINAL, this);
	    puntoInicioHora = new PanelSelectorPunto("Inicio:", ConstantesAcciones.PUNTO_INICIO, this);
	    puntoFinalHora = new PanelSelectorPunto("Final:", ConstantesAcciones.PUNTO_FINAL, this);

	    bloqueDistancia =UtilidadesSwing.createTitledBloque3x1("Distancia", puntoInicioDistancia, puntoFinalDistancia, longitudLabel);
	    bloqueTiempoMov = UtilidadesSwing.createTitledBloque3x1("Tiempo en movimiento",  puntoInicioTiempo, puntoFinalTiempo, tiempoMovimientoLabel);
	    bloqueHora = UtilidadesSwing.createTitledBloque3x1("Hora", puntoInicioHora, puntoFinalHora, tiempoAbsolutoLabel);
	    bloqueDesnivel = UtilidadesSwing.createTitledBloque3x1("Desnivel", desnivelLabel, desnivelAcumuladoLabel, coeficienteLabel);
	    bloqueAltitud = UtilidadesSwing.createTitledBloque3x1("Altitud", altitudMinLabel, altitudMedLabel, altitudMaxLabel);
	    bloqueHR = UtilidadesSwing.createTitledBloque3x1("Pulsaciones",  hrMinLabel, hrMedLabel, hrMaxLabel);
	    bloqueCadencia = UtilidadesSwing.createTitledBloque3x1("Cadencia", cadenciaMinLabel, cadenciaMedLabel, cadenciaMaxLabel);
	    bloquePotencia = UtilidadesSwing.createTitledBloque3x1("Potencia", potenciaMinLabel, potenciaMedLabel, potenciaMaxLabel);	    
	    bloqueVelocidad = UtilidadesSwing.createTitledBloque3x1("Velocidad", velocidadMinLabel, velocidadMedLabel, velocidadMaxLabel);
	    bloquePaso = UtilidadesSwing.createTitledBloque3x1("Paso", pasoMinLabel, pasoMedLabel, pasoMaxLabel);
	    bloquePendiente = UtilidadesSwing.createTitledBloque3x1("Pendiente", pendienteMinLabel, pendienteMedLabel, pendienteMaxLabel);  

	    add(bloqueDistancia);
	    add(bloqueTiempoMov);
	    add(bloqueHora);
	    add(bloqueDesnivel);
	    add(bloqueAltitud);
	    add(bloqueHR);
	    add(bloqueCadencia);
	    add(bloquePotencia);
	    add(bloqueVelocidad);
	    add(bloquePaso);
	    add(bloquePendiente);
	    
	}
	public void update(DatosActividad datosActividad)  {
		this.datosActividad = datosActividad;
		DatosSegmentoBean datosTrack = datosActividad.getDatosTrack();
		DatosSegmentoBean datosSegmento = datosActividad.getDatosSegmentoActual();
		if (datosActividad.getTrack().getTipoActividad().isPaso()) {
			bloquePaso.setVisible(true);
			bloqueVelocidad.setVisible(false);
		} else {
			bloquePaso.setVisible(false);
			bloqueVelocidad.setVisible(true);
		}
		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS()) {
			bloqueAltitud.setVisible(true);
			bloqueDesnivel.setVisible(true);
			bloquePendiente.setVisible(true);
		} else {
			bloqueAltitud.setVisible(false);
			bloqueDesnivel.setVisible(false);
			bloquePendiente.setVisible(false);
		}
			
		if (datosTrack.getHrMed() > 0) bloqueHR.setVisible(true);
		else bloqueHR.setVisible(false);

		if (datosTrack.getCadenciaMed() > 0) bloqueCadencia.setVisible(true);
		else bloqueCadencia.setVisible(false);

		if (datosTrack.getPotenciaMed() > 0) bloquePotencia.setVisible(true);
		else bloquePotencia.setVisible(false);

		setRango(datosActividad.getInicioRango(), datosActividad.getFinRango());
		puntoInicioDistancia.setValor(UtilidadesFormat.getFloatFormat().format((float)datosSegmento.getDistanciaInicial() / 1000) + " km");
		puntoFinalDistancia.setValor(UtilidadesFormat.getFloatFormat().format((float)datosSegmento.getDistanciaFinal() / 1000) + " km");
		puntoInicioTiempo.setValor(UtilidadesFormat.getTiempoFormat().format(new Date(datosSegmento.getTiempoInicial())));
		puntoFinalTiempo.setValor(UtilidadesFormat.getTiempoFormat().format(new Date(datosSegmento.getTiempoFinal())));
		puntoInicioHora.setValor(UtilidadesFormat.getTiempoFormat().format(datosSegmento.getHoraInicio()));
		puntoFinalHora.setValor(UtilidadesFormat.getTiempoFormat().format(datosSegmento.getHoraFin()));
		tiempoAbsolutoLabel.setValor(UtilidadesFormat.getTiempoFormat().format(datosSegmento.getTiempoAbsoluto()) + " horas");
		tiempoMovimientoLabel.setValor(UtilidadesFormat.getTiempoFormat().format(datosSegmento.getTiempoMovimiento()) + " horas");
		longitudLabel.setValor(UtilidadesFormat.getFloatFormat().format(datosSegmento.getLongitud() / 1000.0) + " km");
		desnivelLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getDesnivelTotal()) + " m");
		desnivelAcumuladoLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getDesnivelAcumulado()) + " m");
		coeficienteLabel.setValor(UtilidadesFormat.getDecimalFormat().format(datosSegmento.getCoeficienteAPM()));
		altitudMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getAltitudMed()) + " m");
		altitudMinLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getAltitudMin()) + " m");
		altitudMaxLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getAltitudMax()) + " m");
		hrMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getHrMed()) + " bpm");
		hrMinLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getHrMin()) + " bpm");        
		hrMaxLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getHrMax()) + " bpm");        
		cadenciaMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getCadenciaMed()));
		cadenciaMinLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getCadenciaMin()));
		cadenciaMaxLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getCadenciaMax()));
		potenciaMedLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getPotenciaMed()));
		potenciaMinLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getPotenciaMin()));
		potenciaMaxLabel.setValor(UtilidadesFormat.getIntegerFormat().format(datosSegmento.getPotenciaMax()));
		velocidadMedLabel.setValor(UtilidadesFormat.getDecimalFormat().format(datosSegmento.getVelocidadMed()) + " km/h");        
		velocidadMinLabel.setValor(UtilidadesFormat.getDecimalFormat().format(datosSegmento.getVelocidadMin()) + " km/h");        
		velocidadMaxLabel.setValor(UtilidadesFormat.getDecimalFormat().format(datosSegmento.getVelocidadMax()) + " km/h");    
		pasoMedLabel.setValor(UtilidadesFormat.getPasoFormat().format(datosSegmento.getPasoMed()) + " min/km");        
		pasoMinLabel.setValor(UtilidadesFormat.getPasoFormat().format(datosSegmento.getPasoMin()) + " min/km");        
		pasoMaxLabel.setValor(UtilidadesFormat.getPasoFormat().format(datosSegmento.getPasoMax()) + " min/km");    
		pendienteMedLabel.setValor(UtilidadesFormat.getPercentFormat().format(datosSegmento.getPendienteMed()));        
		pendienteMinLabel.setValor(UtilidadesFormat.getPercentFormat().format(datosSegmento.getPendienteMin()));        
		pendienteMaxLabel.setValor(UtilidadesFormat.getPercentFormat().format(datosSegmento.getPendienteMax()));    	
	}

	
	public void setMinInicioRango(int min)  {
		puntoInicioDistancia.setMin(min);
		puntoInicioTiempo.setMin(min);
		puntoInicioHora.setMin(min);
	}
	
	public void setMaxInicioRango(int max)  {
		puntoInicioDistancia.setMax(max);
		puntoInicioTiempo.setMax(max);
		puntoInicioHora.setMax(max);
	}

	public void setLimitesInicioRango(int min, int max)  {
		setMinInicioRango(min);
		setMaxInicioRango(max);
	}
	
	public void setMinFinRango(int min)  {
		puntoFinalDistancia.setMin(min);
		puntoFinalTiempo.setMin(min);
		puntoFinalHora.setMin(min);
	}
	
	public void setMaxFinRango(int max)  {
		puntoFinalDistancia.setMax(max);
		puntoFinalTiempo.setMax(max);
		puntoFinalHora.setMax(max);
	}
	
	public void setLimitesFinRango(int min, int max)  {
		setMinFinRango(min);
		setMaxFinRango(max);
	}

	public void setRango(int inicioRango, int finRango) {
		puntoInicioDistancia.setPunto(inicioRango);
		puntoInicioTiempo.setPunto(inicioRango);	
		puntoInicioHora.setPunto(inicioRango);	
		puntoFinalDistancia.setPunto(finRango);
		puntoFinalTiempo.setPunto(finRango);
		puntoFinalHora.setPunto(finRango);
		setMinFinRango(inicioRango+1);
		setMaxInicioRango(finRango-1);
	}
	
	public void updateConfiguracion ()  {
		puntoInicioDistancia.update();
		puntoInicioTiempo.update();	
		puntoInicioHora.update();	
		puntoFinalDistancia.update();
		puntoFinalTiempo.update();
		puntoFinalHora.update();
	}


	public void actionPerformed(ActionEvent evento) {
		if (ConstantesAcciones.PUNTO_INICIO.equals(evento.getActionCommand())) {
			PanelSelectorPunto punto = (PanelSelectorPunto) evento.getSource();
			datosActividad.setInicioRango(punto.getPunto());
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}
		if (ConstantesAcciones.PUNTO_FINAL.equals(evento.getActionCommand())) {
			PanelSelectorPunto punto = (PanelSelectorPunto) evento.getSource();
			datosActividad.setFinRango(punto.getPunto());
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		}    	
	}
}

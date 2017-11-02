package aplicaciones.gpsedit.actividad;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import aplicaciones.gpsedit.ConstantesAcciones;
import aplicaciones.gpsedit.GPSEdit;
import aplicaciones.gpsedit.beans.Seccion;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelTablaSecciones extends JPanel implements ListSelectionListener{

	private static final long serialVersionUID = -2823875547147813074L;
	private DefaultTableModel datosSecciones;
	private JTable tablaDatos = new JTable();
	private List<Seccion> secciones;
	private DatosActividad datosActividad;

	private ActionListener listener;

	public PanelTablaSecciones(ActionListener listener)  {
		this.listener = listener;

		
		setLayout(new GridLayout(1,1));
		tablaDatos = new JTable();
		tablaDatos.setFillsViewportHeight(true);
		
        tablaDatos.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDatos.getSelectionModel().addListSelectionListener(this);
        tablaDatos.setColumnSelectionAllowed(false);
        
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		scrollPane.setBounds(0,0,1292,565);
		add(scrollPane);

	}

	public void update(DatosActividad datosActividad) {
		this.datosActividad = datosActividad;
		datosSecciones = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 9106284088842148113L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};		
		tablaDatos.setModel(datosSecciones);
		datosSecciones.addColumn("Secci�n");
		datosSecciones.addColumn("Lon (km)");
		datosSecciones.addColumn("Tmp Abs");
		datosSecciones.addColumn("Tmp Mov");
		if (datosActividad.getTrack().getDatos().getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
			datosSecciones.addColumn("Des Acum");
			datosSecciones.addColumn("Desnivel");
		}
		if (datosActividad.getTrack().getDatos().getHrMed() > 0)  {
			datosSecciones.addColumn("HR Med(ppm)");
			datosSecciones.addColumn("HR M�n(ppm)");
			datosSecciones.addColumn("HR M�x(ppm)");
		}
		if (datosActividad.getTrack().getDatos().getCadenciaMed() > 0)  {
			datosSecciones.addColumn("Cad Med");
			datosSecciones.addColumn("Cad M�n");
			datosSecciones.addColumn("Cad M�x");
		}
		if (datosActividad.getTrack().getDatos().getPotenciaMed() > 0)  {
			datosSecciones.addColumn("Wat Med");
			datosSecciones.addColumn("Wat M�n");
			datosSecciones.addColumn("Wat M�x");
		}
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosSecciones.addColumn("Paso Med(min/km)");
		else datosSecciones.addColumn("Vel Med(km/h)");
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosSecciones.addColumn("Paso M�n(min/km)");
		else datosSecciones.addColumn("Vel M�n(km/h)");
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosSecciones.addColumn("Paso M�x(min/km)");
		else datosSecciones.addColumn("Vel M�x(km/h)");
		if (datosActividad.getTrack().getDatos().getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
			datosSecciones.addColumn("Alt Med(m)");
			datosSecciones.addColumn("Alt M�n(m)");
			datosSecciones.addColumn("Alt M�x(m)");
			datosSecciones.addColumn("Pte Med");
			datosSecciones.addColumn("Pte M�n");
			datosSecciones.addColumn("Pte M�x");
		}
		update();
	}

	public void update() {
		this.secciones = datosActividad.getTrack().getSecciones();
		if (secciones.size() == 1) secciones = datosActividad.getSeccionesAutomaticas();
		datosSecciones.setRowCount(0);
		for (int index = 0; index < secciones.size() ; index++)  {
			Seccion seccion = secciones.get(index);
        	Vector<String> fila = new Vector<String>();
        	fila.add(seccion.getTexto());
        	fila.add(UtilidadesFormat.getDecimalFormat().format(seccion.getDatos().getLongitud() / 1000));
        	
        	fila.add(UtilidadesFormat.getTiempoFormat().format(seccion.getDatos().getTiempoAbsoluto()));
        	fila.add(UtilidadesFormat.getTiempoFormat().format(seccion.getDatos().getTiempoMovimiento()));
    		if (datosActividad.getTrack().getDatos().getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
    			fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getDesnivelAcumulado()));
    			fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getDesnivelTotal()));
    		}
    		if (datosActividad.getTrack().getDatos().getHrMed() > 0)  {
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getHrMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getHrMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getHrMax()));
    		}
    		if (datosActividad.getTrack().getDatos().getCadenciaMed() > 0)  {
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getCadenciaMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getCadenciaMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getCadenciaMax()));
    		}
    		if (datosActividad.getTrack().getDatos().getPotenciaMed() > 0)  {
    			fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getPotenciaMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getPotenciaMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getPotenciaMax()));
    		}
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(seccion.getDatos().getPasoMed()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(seccion.getDatos().getVelocidadMed()));
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(seccion.getDatos().getPasoMin()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(seccion.getDatos().getVelocidadMin()));
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(seccion.getDatos().getPasoMax()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(seccion.getDatos().getVelocidadMax()));
    		if (datosActividad.getTrack().getDatos().getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getAltitudMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getAltitudMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(seccion.getDatos().getAltitudMax()));
	        	fila.add(UtilidadesFormat.getPercentFormat().format(seccion.getDatos().getPendienteMed()));
	        	fila.add(UtilidadesFormat.getPercentFormat().format(seccion.getDatos().getPendienteMin()));
	        	fila.add(UtilidadesFormat.getPercentFormat().format(seccion.getDatos().getPendienteMax()));
    		}
        	datosSecciones.addRow(fila);
		}
	}

	public void valueChanged(ListSelectionEvent evento) {
		if (!evento.getValueIsAdjusting())  {
			tablaDatos.getSelectionModel().removeListSelectionListener(this);
			GPSEdit.logger.debug("cambio selecci�n secciones:" + evento.getFirstIndex());
			Seccion seccion = secciones.get(evento.getFirstIndex());
			datosActividad.setRango(seccion.getInicioRango(), seccion.getFinRango());
			tablaDatos.getSelectionModel().clearSelection();
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
			tablaDatos.getSelectionModel().addListSelectionListener(this);
		}
	}
	

}

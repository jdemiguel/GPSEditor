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
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.Seccion;
import aplicaciones.gpsedit.gps.TrackUtil;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelTablaSecciones extends JPanel implements ListSelectionListener{

	private static final long serialVersionUID = -2823875547147813074L;
	private DefaultTableModel datosSecciones;
	private JTable tablaDatos = new JTable();
	private List<Seccion> secciones;
	private DatosActividad datosActividad;
	private DatosSegmentoBean datosTrack;
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
		datosTrack = datosActividad.getDatosTrack();
		datosSecciones = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 9106284088842148113L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};		
		tablaDatos.setModel(datosSecciones);
		datosSecciones.addColumn("Sección");
		datosSecciones.addColumn("Lon (km)");
		datosSecciones.addColumn("Tmp Abs");
		datosSecciones.addColumn("Tmp Mov");
		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
			datosSecciones.addColumn("Des Acum");
			datosSecciones.addColumn("Desnivel");
		}
		if (datosTrack.getHrMed() > 0)  {
			datosSecciones.addColumn("HR Med(ppm)");
			datosSecciones.addColumn("HR Mín(ppm)");
			datosSecciones.addColumn("HR Máx(ppm)");
		}
		if (datosTrack.getCadenciaMed() > 0)  {
			datosSecciones.addColumn("Cad Med");
			datosSecciones.addColumn("Cad Mín");
			datosSecciones.addColumn("Cad Máx");
		}
		if (datosTrack.getPotenciaMed() > 0)  {
			datosSecciones.addColumn("Wat Med");
			datosSecciones.addColumn("Wat Mín");
			datosSecciones.addColumn("Wat Máx");
		}
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosSecciones.addColumn("Paso Med(min/km)");
		else datosSecciones.addColumn("Vel Med(km/h)");
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosSecciones.addColumn("Paso Mín(min/km)");
		else datosSecciones.addColumn("Vel Mín(km/h)");
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosSecciones.addColumn("Paso Máx(min/km)");
		else datosSecciones.addColumn("Vel Máx(km/h)");
		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
			datosSecciones.addColumn("Alt Med(m)");
			datosSecciones.addColumn("Alt Mín(m)");
			datosSecciones.addColumn("Alt Máx(m)");
			datosSecciones.addColumn("Pte Med");
			datosSecciones.addColumn("Pte Mín");
			datosSecciones.addColumn("Pte Máx");
		}
		update();
	}

	public void update() {
		this.secciones = datosActividad.getTrack().getSecciones();
		if (secciones.size() == 1) secciones = datosActividad.getSeccionesAutomaticas();
		datosSecciones.setRowCount(0);
		for (int index = 0; index < secciones.size() ; index++)  {
			Seccion seccion = secciones.get(index);
			DatosSegmentoBean datos = datosActividad.getDatos(seccion.getInicioRango(), seccion.getFinRango());
        	Vector<String> fila = new Vector<String>();
        	fila.add(seccion.getTexto());
        	fila.add(UtilidadesFormat.getDecimalFormat().format(datos.getLongitud() / 1000));
        	
        	fila.add(UtilidadesFormat.getTiempoFormat().format(datos.getTiempoAbsoluto()));
        	fila.add(UtilidadesFormat.getTiempoFormat().format(datos.getTiempoMovimiento()));
    		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
    			fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getDesnivelAcumulado()));
    			fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getDesnivelTotal()));
    		}
    		if (datosTrack.getHrMed() > 0)  {
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getHrMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getHrMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getHrMax()));
    		}
    		if (datosTrack.getCadenciaMed() > 0)  {
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getCadenciaMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getCadenciaMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getCadenciaMax()));
    		}
    		if (datosTrack.getPotenciaMed() > 0)  {
    			fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getPotenciaMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getPotenciaMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getPotenciaMax()));
    		}
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(datos.getPasoMed()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(datos.getVelocidadMed()));
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(datos.getPasoMin()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(datos.getVelocidadMin()));
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(datos.getPasoMax()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(datos.getVelocidadMax()));
    		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS())  {
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getAltitudMed()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getAltitudMin()));
	        	fila.add(UtilidadesFormat.getIntegerFormat().format(datos.getAltitudMax()));
	        	fila.add(UtilidadesFormat.getPercentFormat().format(datos.getPendienteMed()));
	        	fila.add(UtilidadesFormat.getPercentFormat().format(datos.getPendienteMin()));
	        	fila.add(UtilidadesFormat.getPercentFormat().format(datos.getPendienteMax()));
    		}
        	datosSecciones.addRow(fila);
		}
	}

	public void valueChanged(ListSelectionEvent evento) {
		if (!evento.getValueIsAdjusting())  {
			tablaDatos.getSelectionModel().removeListSelectionListener(this);
			GPSEdit.logger.debug("cambio selección secciones:" + evento.getFirstIndex());
			Seccion seccion = secciones.get(evento.getFirstIndex());
			datosActividad.setRango(seccion.getInicioRango(), seccion.getFinRango());
			tablaDatos.getSelectionModel().clearSelection();
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
			tablaDatos.getSelectionModel().addListSelectionListener(this);
		}
	}
	

}

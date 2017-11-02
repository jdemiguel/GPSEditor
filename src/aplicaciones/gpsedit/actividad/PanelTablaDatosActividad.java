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
import aplicaciones.gpsedit.beans.DatosSegmentoBean;
import aplicaciones.gpsedit.beans.TrackPoint;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelTablaDatosActividad extends JPanel implements ListSelectionListener{

	private static final long serialVersionUID = -2823875547147813074L;
	private DefaultTableModel datosGPS;
	private JTable tablaDatos = new JTable();

	private DatosActividad datosActividad;
	private ActionListener listener;
	private boolean soloSegmento;
	private int inicio;

	public PanelTablaDatosActividad(ActionListener listener, boolean soloSegmento)  {

		
		this.soloSegmento = soloSegmento;
		setLayout(new GridLayout(1,1));
		this.listener = listener;
		tablaDatos = new JTable();
		tablaDatos.setFillsViewportHeight(true);
        
        if (soloSegmento) tablaDatos.getSelectionModel().addListSelectionListener(this);
        tablaDatos.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tablaDatos.setColumnSelectionAllowed(false);
        
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		scrollPane.setBounds(0,0,1292,565);
		add(scrollPane);

	}
	
	public void update(DatosActividad datosActividad) {
		this.datosActividad = datosActividad;
		DatosSegmentoBean datosTrack = datosActividad.getDatosTrack();
		inicio = 0;
		int fin = datosActividad.getTrack().getPuntos().size() - 1;
		if (soloSegmento)  {
			inicio = datosActividad.getInicioRango();
			fin = datosActividad.getFinRango();
		}
		datosGPS = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 9106284088842148113L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		datosGPS.addColumn("Nº");
	    datosGPS.addColumn("Hora");
	    datosGPS.addColumn("Tiempo absoluto");
	    datosGPS.addColumn("Tiempo en movimiento");
		datosGPS.addColumn("Distancia (m)");
		datosGPS.addColumn("Dist. leida (m)");
		if (datosActividad.getTrack().isHayGPS()) datosGPS.addColumn("Dist. calc (m)");
		if (datosTrack.getHrMed() > 0) datosGPS.addColumn("HR (ppm)");
		if (datosTrack.getCadenciaMed() > 0) datosGPS.addColumn("Cadencia");
		if (datosTrack.getPotenciaMed() > 0) datosGPS.addColumn("Potencia");
		if (datosActividad.getTrack().getTipoActividad().isPaso()) datosGPS.addColumn("Paso (min/km)");
		else datosGPS.addColumn("Velocidad (km/h)");
		if (!datosActividad.getTrack().getTipoActividad().isPaso()) {
			datosGPS.addColumn("Velocidad leida (km/h)");
			datosGPS.addColumn("Velocidad calc (km/h)");
		}
		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS()) datosGPS.addColumn("Altitud (m)");
		if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS()) datosGPS.addColumn("Pendiente");
		if (datosActividad.getTrack().isHayGPS()) datosGPS.addColumn("Latitud");
		if (datosActividad.getTrack().isHayGPS()) datosGPS.addColumn("Longitud");
		tablaDatos.setModel(datosGPS);
		
		List<TrackPoint> puntos = datosActividad.getTrack().getPuntos();
		for (int i = inicio; i <= fin ; i++)  {
			TrackPoint punto = puntos.get(i);
        	Vector<String> fila = new Vector<String>();
        	fila.add(UtilidadesFormat.getIntegerFormat().format(i));
        	fila.add(UtilidadesFormat.getMsFormat().format(punto.getHora()));
        	fila.add(UtilidadesFormat.getTiempoFormat().format(punto.getTiempoAbsoluto()));
        	fila.add(UtilidadesFormat.getTiempoFormat().format(punto.getTiempoMovimiento()));
        	fila.add(UtilidadesFormat.getDecimalFormat().format(punto.getDistancia()));
        	fila.add(UtilidadesFormat.getFloatFormat().format(punto.getDistanciaLeida()));
        	if (datosActividad.getTrack().isHayGPS()) fila.add(UtilidadesFormat.getFloatFormat().format(punto.getDistanciaCalculada()));
    		if (datosTrack.getHrMed() > 0) fila.add(UtilidadesFormat.getIntegerFormat().format(punto.getHR()));
    		if (datosTrack.getCadenciaMed() > 0) fila.add(UtilidadesFormat.getIntegerFormat().format(punto.getCadencia()));
    		if (datosTrack.getPotenciaMed() > 0) fila.add(UtilidadesFormat.getIntegerFormat().format(punto.getPotencia()));
        	if (datosActividad.getTrack().getTipoActividad().isPaso()) fila.add(UtilidadesFormat.getPasoFormat().format(punto.getPaso()));
        	else fila.add(UtilidadesFormat.getDecimalFormat().format(punto.getVelocidad()));
        	if (!datosActividad.getTrack().getTipoActividad().isPaso()) {
        		fila.add(UtilidadesFormat.getDecimalFormat().format(punto.getVelocidadLeida()));
        		fila.add(UtilidadesFormat.getDecimalFormat().format(punto.getVelocidadCalculada()));
        	}
        	if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS()) fila.add(UtilidadesFormat.getDecimalFormat().format(punto.getAltitud()));
        	if (datosTrack.getAltitudMed() > 0 && datosActividad.getTrack().isHayGPS()) fila.add(UtilidadesFormat.getPercentFormat().format(datosActividad.getPendiente(i)));
        	if (datosActividad.getTrack().isHayGPS()) fila.add(UtilidadesFormat.getDoubleFormat().format(punto.getLatitud()));
        	if (datosActividad.getTrack().isHayGPS()) fila.add(UtilidadesFormat.getDoubleFormat().format(punto.getLongitud()));
        	datosGPS.addRow(fila);
		}
	}
	
	/**
	 * Selecciona las filas correspondientes al segmento seleccionado 
	 */
	public void updateSeleccion() {
		tablaDatos.getSelectionModel().removeListSelectionListener(this);
		tablaDatos.setRowSelectionInterval(datosActividad.getInicioRango(), datosActividad.getFinRango());
        tablaDatos.getSelectionModel().addListSelectionListener(this);
	}

	public void valueChanged(ListSelectionEvent evento) {
		if (!evento.getValueIsAdjusting() && soloSegmento )  {
			if (evento.getLastIndex() - evento.getFirstIndex() > 2)  {
				tablaDatos.getSelectionModel().removeListSelectionListener(this);
				datosActividad.setInicioRango(evento.getFirstIndex() + inicio);
				datosActividad.setFinRango(evento.getLastIndex() + inicio);
				tablaDatos.getSelectionModel().clearSelection();
				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.CAMBIO_RANGO));
		        tablaDatos.getSelectionModel().addListSelectionListener(this);
			} else {
				tablaDatos.getSelectionModel().removeListSelectionListener(this);
				tablaDatos.getSelectionModel().clearSelection();
		        tablaDatos.getSelectionModel().addListSelectionListener(this);
			}
		}
		if (!evento.getValueIsAdjusting() && !soloSegmento)  {
			updateSeleccion();
		}
	}
	

}

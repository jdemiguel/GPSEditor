package aplicaciones.gpsedit.actividad;


import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import aplicaciones.gpsedit.beans.SegmentoCoeficiente;
import aplicaciones.gpsedit.util.UtilidadesFormat;

public class PanelTablaCoeficientes extends JPanel {

	private static final long serialVersionUID = -2823875547147813074L;
	private DefaultTableModel datosCoeficientes;
	private JTable tablaDatos = new JTable();

	public PanelTablaCoeficientes()  {
		
		setLayout(new GridLayout(1,1));
		tablaDatos = new JTable();
		tablaDatos.setFillsViewportHeight(true);
		
		datosCoeficientes = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 9106284088842148113L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};		
		tablaDatos.setModel(datosCoeficientes);
		datosCoeficientes.addColumn("Número");
		datosCoeficientes.addColumn("Longitud (m)");
		datosCoeficientes.addColumn("Altitud Inicial (m)");
		datosCoeficientes.addColumn("Altitud Final (m)");
		datosCoeficientes.addColumn("Desnivel (m)");
		datosCoeficientes.addColumn("Pendiente (%)");
		datosCoeficientes.addColumn("Coeficiente");		
		
        tablaDatos.setColumnSelectionAllowed(false);
        
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		scrollPane.setBounds(0,0,1292,565);
		add(scrollPane);

	}

	public void update(DatosActividad datosActividad) {
		List<SegmentoCoeficiente> segmentos = datosActividad.getSegmentosCoeficienteAPMRango();
		datosCoeficientes.setRowCount(0);
		for (int index=0; index < segmentos.size(); index++)  {
			SegmentoCoeficiente segmento = segmentos.get(index);
        	Vector<String> fila = new Vector<String>();
        	fila.add(UtilidadesFormat.getIntegerFormat().format(index + 1));
        	fila.add(UtilidadesFormat.getIntegerFormat().format(segmento.getLongitud()));
        	fila.add(UtilidadesFormat.getIntegerFormat().format(segmento.getAlturaInicial()));
        	fila.add(UtilidadesFormat.getIntegerFormat().format(segmento.getAlturaFinal()));
        	fila.add(UtilidadesFormat.getIntegerFormat().format(segmento.getDesnivel()));
        	fila.add(UtilidadesFormat.getPercentFormat().format(segmento.getPendiente()));
        	fila.add(UtilidadesFormat.getDecimalFormat().format(segmento.getCoeficiente()));
        	datosCoeficientes.addRow(fila);			
		}
	}


}

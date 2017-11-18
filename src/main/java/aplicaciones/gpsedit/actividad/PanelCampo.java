package aplicaciones.gpsedit.actividad;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelCampo extends JPanel{

	private static final long serialVersionUID = -6881992786703549860L;
	private JLabel titulo;
	private JLabel valor;
	
	PanelCampo(String titulo)  {
		setLayout(new FlowLayout());
		this.titulo = new JLabel(titulo);		
		this.valor = new JLabel("");		
        add(this.titulo);
        add(this.valor);
	}
	
	public void setValor(String valor) {
		this.valor.setText(valor);
		if(valor.isEmpty()) this.titulo.setVisible(false);
	}
}

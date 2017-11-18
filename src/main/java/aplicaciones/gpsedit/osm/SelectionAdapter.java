package aplicaciones.gpsedit.osm;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;

import aplicaciones.gpsedit.ConstantesAcciones;


/**
 * Creates a selection rectangle based on mouse input
 * Also triggers repaint events in the viewer
 */

public class SelectionAdapter implements MouseInputListener {
	private boolean dragging;
	private JXMapViewer viewer;
	private ActionListener listener;

	private Point2D startPos = new Point2D.Double();
	private Point2D endPos = new Point2D.Double();

	public SelectionAdapter(JXMapViewer viewer, ActionListener listener) {
		this.viewer = viewer;
		this.listener = listener;
	}

	public void mousePressed(MouseEvent evento) {
		if (evento.getClickCount() == 2 && evento.getButton() == MouseEvent.BUTTON1) {
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.RESET_RANGO));
			return;
		}
		if (evento.getButton() != MouseEvent.BUTTON1) return;
		
		startPos.setLocation(evento.getX(), evento.getY());
		endPos.setLocation(evento.getX(), evento.getY());

		dragging = true;
	}

	public void mouseDragged(MouseEvent evento) {
		if (!dragging) return;
		
		endPos.setLocation(evento.getX(), evento.getY());

		viewer.repaint();
	}

	public void mouseReleased(MouseEvent evento) {
		if (!dragging) return;
		if (evento.getButton() != MouseEvent.BUTTON1) return;
		listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ConstantesAcciones.ACTION_SELECT_RECTANGULO));
		viewer.repaint();
		
		dragging = false;
	}

	/**
	 * @return the selection rectangle
	 */
	public Rectangle getRectangle() {
		if (dragging)
		{
			int x1 = (int) Math.min(startPos.getX(), endPos.getX());
			int y1 = (int) Math.min(startPos.getY(), endPos.getY());
			int x2 = (int) Math.max(startPos.getX(), endPos.getX());
			int y2 = (int) Math.max(startPos.getY(), endPos.getY());
			
			return new Rectangle(x1, y1, x2-x1, y2-y1);
		}
		
		return null;
	}
	
	public Point2D getStartPos() {
		return startPos;
	}

	public Point2D getEndPos() {
		return endPos;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

}
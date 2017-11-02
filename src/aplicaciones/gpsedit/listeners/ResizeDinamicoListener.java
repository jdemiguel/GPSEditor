package aplicaciones.gpsedit.listeners;

import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.JComponent;

public class ResizeDinamicoListener implements HierarchyBoundsListener{
	
	public void ancestorMoved(HierarchyEvent e) {
	}

	public void ancestorResized(HierarchyEvent event) {
		if (event.getSource() instanceof JComponent)  {
			JComponent source = (JComponent) event.getSource();
			Rectangle padre = source.getParent().getBounds();
			Rectangle hijo = source.getBounds();
			int altura = (int)padre.getHeight() - (int)hijo.getY();
			if (altura < source.getMinimumSize().getHeight()) altura = (int) source.getMinimumSize().getHeight() - (int)hijo.getY();
			source.setBounds((int)hijo.getX(), (int)hijo.getY(), (int)hijo.getWidth(), altura);

		}
	}
}

/*
 * Creado el 28-oct-2005
 *
 */
package aplicaciones.gpsedit.util;

import java.io.File;
import java.util.ArrayList;

public class FileFilter extends javax.swing.filechooser.FileFilter {

	private ArrayList extensiones;
	private String descripcion;
	
	public FileFilter()  {
		extensiones = new ArrayList();
	}
	
	/**
	 * añade una extension permitida
	 * @param extension
	 */
	public void addExtension(String extension)  {
		extensiones.add(extension);
	}
/**
 * Acepta los directorios y las extensiones añadidas.
 * Si no hay extensiones añadidas acepta todo
 * @param f Fichero
 * @return
 */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extensiones.size()==0 || extensiones.contains(extension)) return true;

        return false;
    }
/**
 * Devuelve la extension de un nombre de fichero
 * @param f
 * @return
 */
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
/**
 * Devuelve la descripcion de este filefilter
 * @return
 */    
    public String getDescription() {
        return descripcion;
    }
    public void setDescription(String description)  {
    	this.descripcion = description;
    }

	
}

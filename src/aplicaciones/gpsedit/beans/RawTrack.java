package aplicaciones.gpsedit.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RawTrack {
	private List<RawSeccion> secciones;
	private Autor autor;
	private String nombre;
	private TipoActividad tipoActividad;
	private Dispositivo dispositivo;
	private String descripcion;
	private long calorias;
	private Date time;
	private boolean hayGPS;
	private boolean hayCadencia;
	private File fichero;
	private String tipo;
	private boolean grabado;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getCalorias() {
		return calorias;
	}

	public void setCalorias(long calorias) {
		this.calorias = calorias;
	}

	public RawTrack() {
		secciones = new ArrayList<RawSeccion>();
	}

	public void addSeccion(RawSeccion seccion)  {
		secciones.add(seccion);
	}
	
	public List<RawSeccion> getSecciones() {
		return secciones;
	}

	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}
	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public Dispositivo getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isHayGPS() {
		return hayGPS;
	}

	public void setHayGPS(boolean hayGPS) {
		this.hayGPS = hayGPS;
	}

	public boolean isHayCadencia() {
		return hayCadencia;
	}

	public void setHayCadencia(boolean hayCadencia) {
		this.hayCadencia = hayCadencia;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isGrabado() {
		return grabado;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setGrabado(boolean grabado) {
		this.grabado = grabado;
	}
	
	
}

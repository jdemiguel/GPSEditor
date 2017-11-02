package aplicaciones.gpsedit.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Track {

	private List<TrackPoint> puntos =  new ArrayList<TrackPoint>();
	private List<Seccion> secciones =  new ArrayList<Seccion>();
	private DatosSegmentoBean datos = new DatosSegmentoBean();
	
	private Autor autor;
	private String nombre;
	private TipoActividad tipoActividad;

	private Dispositivo dispositivo;
	private String descripcion;
	private long calorias;
	private boolean hayGPS;
	
	private File fichero;
	private String tipo;
	private boolean grabado;


	public boolean isHayGPS() {
		return hayGPS;
	}

	public void setHayGPS(boolean hayGPS) {
		this.hayGPS = hayGPS;
	}

	public void setDatos(DatosSegmentoBean datos) {
		this.datos = datos;
	}
	
	public TrackPoint getPrimero() {
		TrackPoint primero = null;
		if (getPuntos().size() > 0) primero = puntos.get(0);
		return primero;
	}
	
	public TrackPoint getUltimo() {
		TrackPoint ultimo = null;
		if (getPuntos().size() > 0) ultimo = puntos.get(getPuntos().size() - 1);
		return ultimo;
		
	}
	
	public void borraUltimo()  {
		if (puntos.size() > 0) removePunto(puntos.get(puntos.size() - 1));
	}
	
	public void addSeccion(Seccion seccion)  {
		secciones.add(seccion);
	}	
	
	public void addPunto(TrackPoint punto)  {
		puntos.add(punto);
	}
	
	public List<TrackPoint> getPuntos() {
		return puntos;
	}
	
	public void removePunto(TrackPoint punto) {
		for (Seccion seccion:secciones) {
			seccion.removePunto(punto);
			if (seccion.size() == 0) {
				secciones.remove(seccion);
			}
		}
		puntos.remove(punto);
	}


	public List<Seccion> getSecciones() {
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
	public void setTipoActividad(TipoActividad tipo) {
		this.tipoActividad = tipo;
	}
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

	public DatosSegmentoBean getDatos() {
		return datos;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}
	
	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isGrabado() {
		return grabado;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setGrabado(boolean grabado) {
		this.grabado = grabado;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
	
	public Track clone()  {
		Track track = new Track();
		track.setAutor(autor.clone());
		track.setCalorias(calorias);
		track.setDatos(datos.clone());
		track.setDescripcion(descripcion);
		track.setDispositivo(dispositivo.clone());
		track.setFichero(new File(fichero.getAbsolutePath()));
		track.setGrabado(grabado);
		track.setHayGPS(hayGPS);
		track.setNombre(nombre);
		track.setTipo(tipo);
		track.setTipoActividad(tipoActividad);
		for (Seccion seccion:secciones)  {
			Seccion seccionNueva = seccion.clone();
			track.addSeccion(seccionNueva);
			for (TrackPoint punto:seccionNueva.getPuntos()) track.addPunto(punto);
		}
		return track;
	}
	
}

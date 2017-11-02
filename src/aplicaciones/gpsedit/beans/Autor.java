package aplicaciones.gpsedit.beans;

public class Autor {
	private String name = "";
	private Long versionMajor = null;
	private Long versionMinor = null;
	private Long buildMajor = null;
	private Long buildMinor = null;
	private String langID = "";
	private String partNumber = "";
	
	public final static Autor GPSEdit = new Autor("GPSEdit", new Long(1), new Long(0), "en", "XXX-XXXXX-XX");
	
	public String getName() {
		return name;
	}
	
	public Autor() {
	}
	
	public Autor(String name, Long versionMajor, Long versionMinor, String langID, String partNumber) {
		this.name = name;
		this.versionMajor = versionMajor;
		this.versionMinor = versionMinor;
		this.langID = langID;
		this.partNumber = partNumber;
	}

	public Long getVersionMajor() {
		return versionMajor;
	}
	public Long getVersionMinor() {
		return versionMinor;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setVersionMajor(Long versionMajor) {
		this.versionMajor = versionMajor;
	}
	public void setVersionMinor(Long versionMinor) {
		this.versionMinor = versionMinor;
	}
	public String toString()  {
		StringBuffer salida = new StringBuffer();
		salida.append(name);
		if (versionMajor != null) salida.append(" " + versionMajor.toString());
		if (versionMinor != null) salida.append("." + versionMinor.toString());
		return salida.toString();
	}
	public Long getBuildMajor() {
		return buildMajor;
	}
	public Long getBuildMinor() {
		return buildMinor;
	}
	public void setBuildMajor(Long buildMajor) {
		this.buildMajor = buildMajor;
	}
	public void setBuildMinor(Long buildMinor) {
		this.buildMinor = buildMinor;
	}
	public String getLangID() {
		return langID;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setLangID(String langID) {
		this.langID = langID;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	public Autor clone()  {
		Autor autor = new Autor();
		autor.setBuildMajor(buildMajor);
		autor.setBuildMinor(buildMinor);
		autor.setLangID(langID);
		autor.setName(name);
		autor.setPartNumber(partNumber);
		autor.setVersionMajor(versionMajor);
		autor.setVersionMinor(versionMinor);
		
		return autor;
	}
}

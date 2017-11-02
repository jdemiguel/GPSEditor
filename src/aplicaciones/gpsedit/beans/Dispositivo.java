package aplicaciones.gpsedit.beans;

public class Dispositivo {
	private String name = "";
	private Long unitId = null;
	private Long productID = null;
	private Long versionMajor = null;
	private Long versionMinor = null;
	private Long buildMajor = null;
	private Long buildMinor = null;
	
	public String getName() {
		return name;
	}
	public Long getUnitId() {
		return unitId;
	}
	public Long getProductID() {
		return productID;
	}
	public Long getVersionMajor() {
		return versionMajor;
	}
	public Long getVersionMinor() {
		return versionMinor;
	}
	public Long getBuildMajor() {
		return buildMajor;
	}
	public Long getBuildMinor() {
		return buildMinor;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public void setProductID(Long productID) {
		this.productID = productID;
	}
	public void setVersionMajor(Long versionMajor) {
		this.versionMajor = versionMajor;
	}
	public void setVersionMinor(Long versionMinor) {
		this.versionMinor = versionMinor;
	}
	public void setBuildMajor(Long buildMajor) {
		this.buildMajor = buildMajor;
	}
	public void setBuildMinor(Long buildMinor) {
		this.buildMinor = buildMinor;
	}
	public String toString()  {
		StringBuffer salida = new StringBuffer();
		salida.append(name);
		if (versionMajor != null) salida.append(" " + versionMajor.toString());
		if (versionMinor != null) salida.append("." + versionMinor.toString());
		return salida.toString();
	}
	
	public Dispositivo clone()  {
		Dispositivo dispositivo = new Dispositivo();
		dispositivo.setBuildMajor(buildMajor);
		dispositivo.setBuildMinor(buildMinor);
		dispositivo.setName(name);
		dispositivo.setProductID(productID);
		dispositivo.setUnitId(unitId);
		dispositivo.setVersionMajor(versionMajor);
		dispositivo.setVersionMinor(versionMinor);
		return dispositivo;
	}

}

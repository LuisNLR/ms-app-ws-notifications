package pe.com.softlite.notifications.dto;

public class ResponseResumen {
	
	private String tipoTramite;
	private String area;
	private String dependenciaActual;
	private Integer cantidadTramites;
	
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDependenciaActual() {
		return dependenciaActual;
	}
	public void setDependenciaActual(String dependenciaActual) {
		this.dependenciaActual = dependenciaActual;
	}
	public Integer getCantidadTramites() {
		return cantidadTramites;
	}
	public void setCantidadTramites(Integer cantidadTramites) {
		this.cantidadTramites = cantidadTramites;
	}
	
}

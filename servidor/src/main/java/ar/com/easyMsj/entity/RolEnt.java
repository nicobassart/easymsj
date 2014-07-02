package ar.com.easyMsj.entity;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rol")
@ManagedBean
public class RolEnt {

	@Id
	private String id;
	
	private String descripcion;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public RolEnt(String id, String desc) {
		this.id = id;
		this.descripcion = desc;
	}
	
	public RolEnt() {
		// TODO Auto-generated constructor stub
	}
	
}

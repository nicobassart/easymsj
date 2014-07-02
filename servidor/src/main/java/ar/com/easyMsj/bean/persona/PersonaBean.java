package ar.com.easyMsj.bean.persona;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Named
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PersonaBean implements Serializable {

	private static final long serialVersionUID = 2868742783741899100L;
	private int id;
	private String nombre;
	private String apellido;
	private String tipoDoc;
	private Integer numeroDoc;
	private Date fechaNacimiento;
	private String sexo;
	private String telefono;
	private String celular;
	private String email;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}



	public Integer getNumeroDoc() {
	    return numeroDoc;
	}

	public void setNumeroDoc(Integer numeroDoc) {
	    this.numeroDoc = numeroDoc;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
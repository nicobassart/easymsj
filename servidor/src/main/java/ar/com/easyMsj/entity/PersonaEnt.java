package ar.com.easyMsj.entity;

import java.sql.Blob;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="persona")
@ManagedBean
public class PersonaEnt <T> implements Iterable<T>{ 
	@Id @GeneratedValue
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

	@Column(name="qre",columnDefinition="blob")
	private Blob qre;
	
	@OneToMany(mappedBy = "persona")
	private List<UsuarioEnt> usuarios;

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
	public Blob getQre() {
		return qre;
	}
	public void setQre(Blob qre) {
		this.qre = qre;
	}
	public List<UsuarioEnt> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioEnt> usuarios) {
		this.usuarios = usuarios;
	}
	
	public int getEdad() throws ParseException{
 	        Calendar fechaNacimiento = Calendar.getInstance();
 	        Calendar fechaActual = Calendar.getInstance();
 	        
	 	   	Date date = this.getFechaNacimiento();
 	        
 	        fechaNacimiento.setTime(date);
 	        //Se restan la fecha actual y la fecha de nacimiento
 	        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
 	        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
 	        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
 	        //Se ajusta el año dependiendo el mes y el día
 	        if(mes<0 || (mes==0 && dia<0)){
 	            año--;
 	        }
         //Regresa la edad en base a la fecha de nacimiento
        return año;
	 }
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}

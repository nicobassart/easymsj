package ar.com.instMessenger.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="agenda")
public class Agenda implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue   @Column(name = "id_agenda")
	private int id_agenda;
	private String nombre;
	private String descripcion;
	
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "aux_agenda_persona", joinColumns = { @JoinColumn(name = "id_agenda") }, inverseJoinColumns = { @JoinColumn(name = "id_persona") })
	private List<Persona> personas;
	
	public int getId_agenda() {
		return id_agenda;
	}

	public void setId_agenda(int id_agenda) {
		this.id_agenda = id_agenda;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
        @Override
        public boolean equals(Object obj) {
        	if (obj == null)
        	    return false;
        	if (obj == this)
        	    return true;
        	if (!(obj instanceof Agenda))
        	    return false;
        	return ( (((Agenda) obj).getId_agenda() == this.getId_agenda()) || (((Agenda) obj).getNombre().equals(this.getNombre())));
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
	
	
}

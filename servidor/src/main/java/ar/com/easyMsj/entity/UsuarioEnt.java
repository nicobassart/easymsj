package ar.com.easyMsj.entity;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="usuario")
@ManagedBean
public class UsuarioEnt implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private int id;
	private String userName;
	private String password;
	private String mail;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_persona")
	private PersonaEnt persona;
	
	private String tipoUsuario;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "usuario_rol", joinColumns = { @JoinColumn(name = "id_usuario") }, inverseJoinColumns = { @JoinColumn(name = "id_rol") })
	private List<RolEnt> roles;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RolEnt> getRoles() {
		return roles;
	}

	public void setRoles(List<RolEnt> roles) {
		this.roles = roles;
	}
	
	public String getCommaSeparatedStringRoles(){
		String roles = "";
		for (RolEnt rol : this.getRoles()) {
			roles += "," + rol.getId();
		}
		return roles.replaceFirst(",", "");//para sacar la coma del principio
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public PersonaEnt getPersona() {
		return persona;
	}

	public void setPersona(PersonaEnt persona) {
		this.persona = persona;
	}
	
	
}

package ar.com.easyMsj.bean.usuarios;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Named
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 2868742783741899100L;
	
	private String userName;
	private String password;
	private String mail;
	private String tipoUsuario;

	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

}
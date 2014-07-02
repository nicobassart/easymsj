package ar.com.easyMsj.bean.usuarios;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import ar.com.easyMsj.bean.persona.PersonaBean;
import ar.com.easyMsj.entity.UsuarioEnt;
import ar.com.easyMsj.servicios.dao.IUsuariosDao;

@Named
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 2868742783741899100L;
	private Boolean isAdmin = false;
	private String userName = "";

	private String password = "";
	
	private String mail = "";
	
	private String nombre = "";
	
	private String apellido = "";
	
	private String tipoDoc = "";

	@Resource(name = "authenticationManager")
	private AuthenticationManager am;

	@Autowired
	IUsuariosDao usuariosDAO;
	
	

	public LoginBean() {

	}

	// ActionEvent actionEvent
	public String login() throws java.io.IOException {
		try {
			Authentication req = new UsernamePasswordAuthenticationToken(this.getUserName(), getPassword());
			
			Authentication result = am.authenticate(req);
			
			SecurityContextHolder.getContext().setAuthentication(result);
			
			User user = (User) result.getPrincipal();
			
			
			SecurityContextHolder.getContext().getAuthentication().getDetails();
			System.out.println("Login Success! ..");

			//HttpRequest request = (HttpRequest)	 FacesContext.getCurrentInstance().getExternalContext().getRequest();

			// System.out.println("is Admin : "+request.isUserInRole("ROLE_ADMIN"));
			isAdmin = true;

			return "/pages/main.xhtml";
		} catch (IllegalArgumentException ie) {
			System.out.println("Login Failed on IllegalArgumentException");
			System.out.println(ie.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(
							"formLogin",
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Login Failed",
									"User Name and Password Not Match!"));

			return "/login";
		}
	}

	public String logout() {
		// System.out.println("LoginBean.logout()....");
		SecurityContextHolder.getContext().setAuthentication(null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.clear();
		return "/login";
	}

	public String getLogoutHidden() {
		// System.out.println("LoginBean.getLogoutHidden()....");
		SecurityContextHolder.getContext().setAuthentication(null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.clear();
		return "logout";
	}

	public void setLogoutHidden(String logoutHidden) {
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
	
	

}
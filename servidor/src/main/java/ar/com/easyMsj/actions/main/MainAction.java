package ar.com.easyMsj.actions.main;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ar.com.easyMsj.actions.Action;
import ar.com.easyMsj.bean.persona.PersonaBean;
import ar.com.easyMsj.bean.usuarios.LoginBean;
import ar.com.easyMsj.bean.usuarios.UsuarioBean;
import ar.com.easyMsj.entity.UsuarioEnt;
import ar.com.easyMsj.exceptions.UsuarioExistenteException;
import ar.com.easyMsj.servicios.dao.IUsuariosDao;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class MainAction extends Action implements IMainAction {

    private static final long serialVersionUID = -2261476298684088030L;

    @Autowired
    private PersonaBean personaBean;
    
	@Autowired
	UsuarioBean usuarioLogueada;


    @Autowired
    LoginBean loginBea;
    
    @Autowired
    IUsuariosDao usuarioDao;
    
	@Resource(name = "authenticationManager")
	private AuthenticationManager am;

    
    private String texto;
    
	// ActionEvent actionEvent
	public String login() throws java.io.IOException {
		try {
			Authentication req = new UsernamePasswordAuthenticationToken(loginBea.getUserName(), loginBea.getPassword());
			
			Authentication result = am.authenticate(req);
			
			SecurityContextHolder.getContext().setAuthentication(result);
			
			User user = (User) result.getPrincipal();
			
			
			SecurityContextHolder.getContext().getAuthentication().getDetails();
			System.out.println("Login Success! ..");

			//HttpRequest request = (HttpRequest)	 FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			UsuarioEnt usuEnt= usuarioDao.getUsuario(user.getUsername());
			
			usuarioLogueada.setTipoUsuario(usuEnt.getTipoUsuario());
			personaBean.setApellido(usuEnt.getPersona().getApellido());
			personaBean.setNombre(usuEnt.getPersona().getNombre());
			personaBean.setId(usuEnt.getPersona().getId());
			personaBean.setTipoDoc(usuEnt.getPersona().getTipoDoc());
			personaBean.setNumeroDoc(usuEnt.getPersona().getNumeroDoc());


			return "success";
		} catch (Exception ie) {
			System.out.println("Login Failed on IllegalArgumentException");
			System.out.println(ie.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(
							"formLogin",
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Usuario o Password Incorrecto",
									"User Name and Password Not Match!"));

			return "notSuccess";
		}
	}
    
	public String primerLogin() throws java.io.IOException {
		try {

			UsuarioEnt usuEnt= usuarioDao.crearUsuario(loginBea.getTipoDoc(), loginBea.getUserName(), loginBea.getMail(), loginBea.getPassword(), loginBea.getApellido(), loginBea.getNombre());
			
			
			usuarioLogueada.setTipoUsuario(usuEnt.getTipoUsuario());
			personaBean.setApellido(usuEnt.getPersona().getApellido());
			personaBean.setNombre(usuEnt.getPersona().getNombre());
			personaBean.setId(usuEnt.getPersona().getId());
			personaBean.setTipoDoc(usuEnt.getPersona().getTipoDoc());
			personaBean.setNumeroDoc(usuEnt.getPersona().getNumeroDoc());
			
			
			Authentication req = new UsernamePasswordAuthenticationToken(loginBea.getUserName(), loginBea.getPassword());
			Authentication result = am.authenticate(req);
			SecurityContextHolder.getContext().setAuthentication(result);
			
			User user = (User) result.getPrincipal();
			
			SecurityContextHolder.getContext().getAuthentication().getDetails();
			System.out.println("Login Success! ..");
			

			return "success";
		} catch (UsuarioExistenteException e){
			FacesContext.getCurrentInstance()
			.addMessage(
					"formLogin",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Número de documento ya registrado.",
							""));
			return "notSuccess";
		}
		catch (NoResultException e) {
			System.out.println("LA CLAVE NO ES CORRECTA");
			FacesContext.getCurrentInstance()
			.addMessage(
					"formLogin",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"La clave ingresada no es correcta.",
							"User Name and Password Not Match!"));
			return "notSuccess";
		}
		catch (Exception ie) {
			System.out.println("Login Failed on IllegalArgumentException");
			System.out.println(ie.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(
							"formLogin",
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Falló el Log in, intente nuevamente por favor.",
									"User Name and Password Not Match!"));

			return "notSuccess";
		}
	}
	
    public String logout() {
	// System.out.println("LoginBean.logout()....");
	SecurityContextHolder.getContext().setAuthentication(null);
	//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
	 FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "success";
    }
	
    public String enviarMsj() {
		return "enviadoOK";
    	
    }
    
	public String getTexto() {
        return texto;
    }

}

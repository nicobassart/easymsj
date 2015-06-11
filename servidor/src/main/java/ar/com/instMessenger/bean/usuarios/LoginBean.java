package ar.com.instMessenger.bean.usuarios;





import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ar.com.instMessenger.entity.Persona;


@ManagedBean
@SessionScoped
@Component
public class LoginBean  implements Serializable {

        private static final long serialVersionUID = 2868742783741899100L;
        private Persona person = new Persona();
        private Boolean isAdmin = false;
    private String userName = "Usuario";
        
    private String password = "Usuario";

        

    @Resource(name = "authenticationManager")
    private AuthenticationManager am;
        
    public LoginBean() {

    }

    //ActionEvent actionEvent
        public String login() throws java.io.IOException {
                try {
            Authentication req = new UsernamePasswordAuthenticationToken(this.getUserName(), getPassword());
            Authentication result = am.authenticate(req);
            SecurityContextHolder.getContext().setAuthentication(result);
            //person = (Persona) SecurityContextHolder.getContext().getAuthentication().getDetails();
            System.out.println("Login Success! ..");
            
            //HttpRequest request = (HttpRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
//            System.out.println("is Admin : "+request.isUserInRole("ROLE_ADMIN"));
            isAdmin = true;
            
            return "/pages/envioMsj/envioAlerta.xhtml";
                } catch (IllegalArgumentException ie)  {
                        System.out.println("Login Failed on IllegalArgumentException");
                        System.out.println(ie.getMessage());
                FacesContext.getCurrentInstance().addMessage("formLogin", new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Failed", "User Name and Password Not Match!"));  
             
            return "/login";
        }
        }

        public String logout() {
                //System.out.println("LoginBean.logout()....");
                SecurityContextHolder.getContext().setAuthentication(null);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                                .clear();
                return "/login";
        }
        
        public String getLogoutHidden() {
                //System.out.println("LoginBean.getLogoutHidden()....");
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

        public Persona getPerson() {
                return person;
        }

        public void setPerson(Persona person) {
                this.person = person;
        }

        public Boolean getIsAdmin() {
                return isAdmin;
        }

        public void setIsAdmin(Boolean isAdmin) {
                this.isAdmin = isAdmin;
        }


        
        
}
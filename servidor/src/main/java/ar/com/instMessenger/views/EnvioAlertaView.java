package ar.com.instMessenger.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Named
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class EnvioAlertaView implements Serializable{
	private static final long serialVersionUID = -8725702349262618823L;
	/**
	 * Es invocado desde la pagina para completar el @NOMBRE o @APELLIDO
	 */
	    public List<String> completeArea(String query) {  
	        List<String> results = new ArrayList<String>();  
	          
	        if(query.equals("@")) {  
	            results.add("@NOMBRE");  
	            results.add("@APELLIDO");  
	        } 	          
	        return results;  
	    }  
}

package ar.com.instMessenger.bean.utils;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import ar.com.instMessenger.bean.Bean;
import ar.com.instMessenger.entity.Persona;

@Named
@Scope(value = WebApplicationContext.SCOPE_GLOBAL_SESSION)
public class UtilsBean extends Bean {
	
	private static final long serialVersionUID = 1L;
	private static final String APELLIDO = "@APELLIDO";
	private static final String NOMBRE = "@NOMBRE";

	/** 
     * Aca tengo que armar algo como para que le pueda pasar un texto 
     * con los @NOMBRE y @APELLIDO lo reemplace por lo que corresponda.
     **/
	public String reemplazarContenido(String texto, Persona persona){
	       texto = texto.replaceAll(NOMBRE, persona.getNombre());
	       texto = texto.replaceAll(NOMBRE.toLowerCase(), persona.getNombre());
	       texto = texto.replaceAll(APELLIDO, persona.getApellido());
			return texto.replaceAll(APELLIDO.toLowerCase(), persona.getApellido());
	}
}

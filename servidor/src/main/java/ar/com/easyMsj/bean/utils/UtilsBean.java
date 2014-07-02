package ar.com.easyMsj.bean.utils;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import ar.com.easyMsj.bean.Bean;

@Named
@Scope(value = WebApplicationContext.SCOPE_GLOBAL_SESSION)
public class UtilsBean extends Bean {
	
	private static final long serialVersionUID = 1L;
	private static final String APELLIDO = "@APELLIDO";
	private static final String NOMBRE = "@NOMBRE";

	
}

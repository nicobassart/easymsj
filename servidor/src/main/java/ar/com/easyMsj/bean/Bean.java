package ar.com.easyMsj.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bean implements Serializable{
	
	private String mensaje;
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return this.mensaje;
	} 
}
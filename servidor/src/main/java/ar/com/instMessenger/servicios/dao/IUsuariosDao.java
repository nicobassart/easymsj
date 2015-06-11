package ar.com.instMessenger.servicios.dao;

import java.util.List;

import ar.com.instMessenger.entity.Usuario;

public interface IUsuariosDao {
	public List<Usuario> getUsuarios();
	public Usuario getUsuario(String userName);
	public void crearUsuario(String nombre, String apellido, String mail, String userName);

}

package ar.com.easyMsj.servicios.dao;

import java.sql.Blob;
import java.util.List;

import javax.persistence.NoResultException;

import ar.com.easyMsj.entity.UsuarioEnt;
import ar.com.easyMsj.exceptions.UsuarioExistenteException;

public interface IUsuariosDao {
	public List<UsuarioEnt> getUsuarios();
	public UsuarioEnt getUsuario(String userName);
	public UsuarioEnt crearUsuario(String tipoDoc, String numeroDocumento, String mail, String pclave, String apellido, String nombre) throws UsuarioExistenteException,  NoResultException;
}

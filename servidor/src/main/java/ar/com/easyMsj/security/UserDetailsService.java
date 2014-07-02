package ar.com.easyMsj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.easyMsj.entity.UsuarioEnt;
import ar.com.easyMsj.servicios.dao.IUsuariosDao;

public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	IUsuariosDao usuariosDAO;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		UsuarioEnt usuario = usuariosDAO.getUsuario(username);

		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(username, usuario.getPassword(), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(usuario
						.getCommaSeparatedStringRoles()));
	}

}
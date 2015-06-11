package ar.com.instMessenger.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {


		return new User(username, username, true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
	}

}
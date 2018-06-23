package pl.edu.wat.gouompbackend.app.security;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.gouompbackend.app.user.dao.interfaces.UserDao;
import pl.edu.wat.gouompbackend.app.user.model.User;

@Service
@FieldDefaults(level = PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {

	UserDao userDao;

	public UserDetailsServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User applicationUser = userDao.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new org.springframework.security.core.userdetails.User(
				applicationUser.getUsername(), applicationUser.getPasswordHash(), emptyList()
		);
	}

}
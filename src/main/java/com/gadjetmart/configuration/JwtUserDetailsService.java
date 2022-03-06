package com.gadjetmart.configuration;

import com.gadjetmart.constants.AppConstants;
import com.gadjetmart.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private AuthService authService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.gadjetmart.entity.User user = authService.findUserByEmail(email);
		if (user != null) {
			List<GrantedAuthority> listAuthorities = new ArrayList<>();
			listAuthorities.add(new Role(user.getRole() == null ? AppConstants.USER : user.getRole()));
			return new User(email, user.getPassword(), listAuthorities);
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}

}
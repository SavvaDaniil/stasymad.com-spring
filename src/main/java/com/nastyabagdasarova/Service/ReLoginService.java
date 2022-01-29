package com.nastyabagdasarova.Service;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class ReLoginService {
	@SuppressWarnings("unchecked")
	public boolean init(String username, String password) {
		Collection<SimpleGrantedAuthority> nowAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				username, password, nowAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return true;
	}
}

/*
package com.tp.config.security;

import com.tp.dto.admin.UserDTO;
import com.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = null;
        try {
            user = userService.findByUserName(username);
        } catch (Exception e) {

        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
		CustomUserDetails customUser = new CustomUserDetails(user.getUserName(), user.getPassWord(), getGrantedAuthorities(user));
        customUser.setFullName(user.getFullName());
        return customUser;
	}

	private List<GrantedAuthority> getGrantedAuthorities(UserDTO user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
		return authorities;
	}

}
*/

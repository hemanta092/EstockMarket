package com.stockmarket.command.service;

import com.stockmarket.command.entity.Users;
import com.stockmarket.command.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDataService implements UserDetailsService {

	@Autowired
    private IUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Users user = userRepo.findByUserName(username);
	        if (user != null) {
	        	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	        	String[] roles = user.getRoles().split(",");
	            for (String role : roles) {
	                authorities.add(new SimpleGrantedAuthority(role));
	            }
	            return new User(user.getUserName(), user.getPassword(), authorities);
	        } else {
	            throw new UsernameNotFoundException("User not found with username: " + username);
	        }
	}

}

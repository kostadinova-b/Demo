package se.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import se.details.CustomUserDetails;
import se.model.User;
import se.model.Visitor;
import se.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
	
	private final String USER_NOT_FOUND_MSG = "User %s not found!";
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if(!user.isPresent())
			throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
		if(user.get() instanceof Visitor)
			return new CustomUserDetails(user.get(), "visitor");
		
		return new CustomUserDetails(user.get(), "organizer");
	}

}

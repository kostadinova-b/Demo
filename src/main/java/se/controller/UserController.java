package se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.details.CustomUserDetails;
import se.model.User;
import se.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public User getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
		User user = userService.getUser(userDetails.getUserId(), userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_organizer")));

		return user;
	}

	@PutMapping("/{roles}/{id}")
	public void updateUser(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody User user) {
		user.setId(userDetails.getUserId());
		userService.updateUser(user);
	}
	
	@DeleteMapping
	public void deleteUser(@AuthenticationPrincipal CustomUserDetails user) {
		userService.deleteUser(user.getUserId(), user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_organizer")));
	}

}

package se.details;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import se.model.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -3262799676785947649L;

	private String username;
	private String password;
	private String role;
	private boolean enabled;
	private long userId;

	public CustomUserDetails() {

	}

	public CustomUserDetails(User user, String role) {
		username = user.getUsername();
		password = user.getPassword();
		enabled = user.isEnabled();
		this.role = role;
		this.userId = user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public long getUserId() {
		return userId;
	}

}

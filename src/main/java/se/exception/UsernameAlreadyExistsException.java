package se.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Username already exists!", value = HttpStatus.CONFLICT)
public class UsernameAlreadyExistsException extends RuntimeException{

	public UsernameAlreadyExistsException(String username) {
		super(username);
	}

	private static final long serialVersionUID = 1L;

}

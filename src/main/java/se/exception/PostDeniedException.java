package se.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PostDeniedException extends RuntimeException{

	public PostDeniedException(String event) {
		super(event);		
	}
	
	private static final long serialVersionUID = 2855085957180433161L;

}

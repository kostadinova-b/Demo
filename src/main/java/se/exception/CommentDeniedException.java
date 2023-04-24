package se.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CommentDeniedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5194216715735804395L;

}

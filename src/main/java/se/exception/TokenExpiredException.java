package se.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class TokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -791806376632166695L;

}

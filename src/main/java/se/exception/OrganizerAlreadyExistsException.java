package se.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OrganizerAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -2609142166930720205L;

}

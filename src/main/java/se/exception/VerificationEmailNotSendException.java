package se.exception;

public class VerificationEmailNotSendException extends RuntimeException {

	private static final long serialVersionUID = 5977173979605322263L;

	public VerificationEmailNotSendException(String email) {
		super(email);
	}

}

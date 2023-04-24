package se.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import se.details.EmailDetails;

@Service
public interface EmailService {
	
	void sendEmail(EmailDetails details) throws MessagingException;

}

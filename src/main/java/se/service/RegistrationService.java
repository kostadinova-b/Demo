package se.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import se.model.Visitor;

@Service
public interface RegistrationService {
	
	void register(Visitor visitor);
	void verifyRegistration(String token, Timestamp time);
}

package se.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.model.Visitor;
import se.service.RegistrationService;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private RegistrationService registrationService;

	@PostMapping
	public void register(@RequestBody Visitor visitor) {
		registrationService.register(visitor);
	}

	@GetMapping("/verify")
	public void verifyRegistration(@RequestParam String code) {
		registrationService.verifyRegistration(code, new Timestamp(System.currentTimeMillis()));

	}

}

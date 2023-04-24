package se.service.implementation;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.bytebuddy.utility.RandomString;
import se.details.EmailDetails;
import se.exception.TokenExpiredException;
import se.exception.TokenNotFoundException;
import se.exception.UsernameAlreadyExistsException;
import se.exception.VerificationEmailNotSendException;
import se.model.VerificationToken;
import se.model.Visitor;
import se.repository.UserRepository;
import se.repository.VerificationTokenRepository;
import se.service.EmailService;
import se.service.RegistrationService;

@Component
public class RegistrationServiceImpl implements RegistrationService {

	private final String VERIFICATION_MESSAGE = "Hello!<br>Click the link below to verify your registration:<br><h3><a href=\"%s\" target=\"_self\">VERIFY</a></h3>Thank you,<br>EventSocial";

	@Autowired
	VerificationTokenRepository tokenRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	EmailService emailService;
	@Autowired
	TaskExecutor taskExecutor;

	@Value("${custom.base.url}")
	private String baseURL;
	@Value("${custom.verification.delay.minutes}")
	private int delayMinutes;

	@Override
	@Transactional
	public void register(Visitor visitor) {
		Timestamp expiryTime = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(delayMinutes));
		String stringToken = RandomString.make(64);
		String verificaionURL = baseURL + "/register/verify?code=" + stringToken;
		createVerificationToken(visitor, stringToken, expiryTime);
		sendVerificationEmail(visitor, verificaionURL);

	}

	@Override
	public void verifyRegistration(String token, Timestamp time) {
		VerificationToken vtoken = tokenRepository.findByToken(token).orElse(null);
		if (vtoken == null)
			throw new TokenNotFoundException();
		if (vtoken.getExipryTime().getTime() < System.currentTimeMillis())
			throw new TokenExpiredException();

		enableVisitor(vtoken.getVisitor());
	}

	@Transactional
	private Visitor enableVisitor(Visitor visitor) {
		visitor.setEnabled(true);
		userRepository.saveAndFlush(visitor);
		return visitor;
	}

	private void sendVerificationEmail(Visitor visitor, String siteURL) {
		EmailDetails details = new EmailDetails.Builder(visitor.getEmail()).setSubject("Activation link")
				.hasHTMLBody(true).setMessage(String.format(VERIFICATION_MESSAGE, siteURL)).build();

		taskExecutor.execute(() -> {
			try {
				emailService.sendEmail(details);
			} catch (MessagingException e) {
				throw new VerificationEmailNotSendException(visitor.getEmail());
			}
		});

	}

	@Transactional
	private VerificationToken createVerificationToken(Visitor visitor, String token, Timestamp expiryTime) {
		visitor.setPassword(passwordEncoder.encode(visitor.getPassword()));
		VerificationToken vtoken = new VerificationToken(visitor, token, expiryTime);
		try {
			tokenRepository.saveAndFlush(vtoken);
		} catch (DataIntegrityViolationException e) {
			throw new UsernameAlreadyExistsException(visitor.getUsername());
		}

		return vtoken;
	}

}

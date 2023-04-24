package se.service.implementation;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import se.details.EmailDetails;
import se.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Async
	@Override
	public void sendEmail(EmailDetails details) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(details.getSubject());
		helper.setFrom(sender);
		helper.setTo(details.getRecipient());
		helper.setText(details.getMsgBody(), details.hasHtmlBody());

		if (details.getAttachments() != null) {
			for (String attachment : details.getAttachments()) {
				FileSystemResource file = new FileSystemResource(attachment);
				helper.addAttachment(file.getFilename(), file);
			}
		}

		javaMailSender.send(message);

	}

}

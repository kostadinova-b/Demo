package se.details;

import java.io.Serializable;

public class EmailDetails implements Serializable {

	private static final long serialVersionUID = -7574590323976061373L;

	private String recipient;
	private String subject;
	private String msgBody;
	private boolean htmlBody;
	private String[] attachments;

	private EmailDetails() {

	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public boolean hasHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(boolean htmlBody) {
		this.htmlBody = htmlBody;
	}
	
	public String[] getAttachments() {
		return attachments;
	}

	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}


	public static class Builder {

		private String recipient;
		private String subject;
		private String[] attachments;
		private String msgBody;
		private boolean HTMLBody;

		public Builder(String recipient) {
			this.recipient = recipient;
		}

		public Builder setSubject(String subject) {
			this.subject = subject;

			return this;
		}

		public Builder setAttachments(String[] attachments) {
			this.attachments = attachments;

			return this;
		}

		public Builder setMessage(String msgBody) {
			this.msgBody = msgBody;

			return this;
		}

		public Builder hasHTMLBody(boolean HTMLBody) {
			this.HTMLBody = HTMLBody;

			return this;
		}

		public EmailDetails build() {
			EmailDetails email = new EmailDetails();
			email.setAttachments(attachments);
			email.setHtmlBody(HTMLBody);
			email.setMsgBody(msgBody);
			email.setRecipient(recipient);
			email.setSubject(subject);

			return email;

		}

	}

}

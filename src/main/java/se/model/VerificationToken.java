package se.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verify_table")
public class VerificationToken implements Serializable {

	private static final long serialVersionUID = 3794742674180295876L;

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Visitor visitor;

	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private Timestamp expiryTime;

	public VerificationToken() {

	}

	public VerificationToken(Visitor visitor, String token, Timestamp timestamp) {
		super();
		this.visitor = visitor;
		this.token = token;
		this.expiryTime = timestamp;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getExipryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Timestamp timestamp) {
		this.expiryTime = timestamp;
	}

}

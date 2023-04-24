package se.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment implements Serializable{

	private static final long serialVersionUID = -7551388753275267130L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "userid", updatable = false, insertable = false)
	private User user;
	@Column(nullable = false)
	private Long userid;

	@ManyToOne
	@JoinColumn(name = "postid", updatable = false, insertable = false)
	private Post post;
	@Column(nullable = false)
	private Long postid;
	@Column(nullable = false)
	private Timestamp postTime;

	public Comment() {

	}

	public Comment(String comment, Long userid, Long postid, Timestamp timestamp) {
		super();
		this.comment = comment;
		this.userid = userid;
		this.postid = postid;
		this.postTime = timestamp;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonBackReference(value = "user-comment")
	public User getUser() {
		return user;
	}

	@JsonBackReference(value = "post-comment")
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Long getPostid() {
		return postid;
	}

	public void setPostid(Long postid) {
		this.postid = postid;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp timestamp) {
		this.postTime = timestamp;
	}

}

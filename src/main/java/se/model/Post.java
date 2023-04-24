package se.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "posts")
public class Post implements Serializable {

	private static final long serialVersionUID = 3847369556295817686L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

	@ManyToOne
	@JoinColumn(name = "eventid", insertable = false, updatable = false)
	private Event event;

	@Column(nullable = false)
	private Long eventid;

	@Column(nullable = false)
	private Timestamp postTime;

	@Column(nullable = false)
	private String comment;

	public Post() {

	}

	public Post(Event event, Timestamp timestamp, String comment) {
		super();
		this.event = event;
		this.postTime = timestamp;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventid() {
		return eventid;
	}

	public void setEventid(Long eventid) {
		this.eventid = eventid;
	}

	@JsonManagedReference(value = "post-comment")
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@JsonBackReference(value = "event-post")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp timestamp) {
		this.postTime = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, event, postTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(event, other.event)
				&& Objects.equals(postTime, other.postTime);
	}

}

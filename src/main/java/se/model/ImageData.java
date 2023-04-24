package se.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonBackReference;

import se.exception.ResourceLocationGenerationException;

@Entity
public class ImageData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String location;

	@Transient
	private byte[] imgData;
	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;
	private long userid;
	@ManyToOne
	@JoinColumn(name = "eventid", insertable = false, updatable = false)
	private Event event;
	private long eventid;
	
	@Value("${custom.imgDir}")
	transient private String imgDir;
	
	public ImageData() {

	}

	public ImageData(Long eventId, Long userId, byte[] imgData) throws ResourceLocationGenerationException {

		location = generateImageAbsolutePath(eventId, userId);
		this.eventid = eventId;
		this.userid = userId;
		this.imgData = imgData;
	}

	private String generateImageAbsolutePath(Long eventId, Long userId) {
		String imageName = Long.valueOf(System.currentTimeMillis()) + ".jpg";
		Path path = Paths.get(imgDir, eventId.toString(), userId.toString(), imageName);

		if (path == null)
			throw new ResourceLocationGenerationException();
		return path.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String name) {
		this.location = name;
	}

	public byte[] getImgData() {
		return imgData;
	}

	public void setImgData(byte[] imgData) {
		this.imgData = imgData;
	}

	@JsonBackReference(value = "user-image")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonBackReference(value = "event-image")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getEventid() {
		return eventid;
	}

	public void setEventid(long eventid) {
		this.eventid = eventid;
	}

}

package se.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DiscriminatorValue("organizer")
public class Organizer extends User {

	private static final long serialVersionUID = 8672538734177011063L;
	@OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Event> events;

	public Organizer() {
		super();
	}

	public Organizer(String username, String password, String email) {
		super(username, password, email);
	}

	@JsonManagedReference(value = "organizer-event")
	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}

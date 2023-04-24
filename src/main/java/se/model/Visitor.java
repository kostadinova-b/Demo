package se.model;

import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("visitor")
public class Visitor extends User {

	private static final long serialVersionUID = 461841017217746566L;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "visitors_events", joinColumns = @JoinColumn(name = "visitorid"), inverseJoinColumns = @JoinColumn(name = "eventid"))
	private Set<Event> events;

	public Visitor() {
		super();
	}

	public Visitor(String username, String password, String email) {
		super(username, password, email);
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public void addEvent(Event event) {
		events.add(event);
		event.getVisitors().add(this);
	}

	public void removeEvent(Long eventId) {
		Optional<Event> event = events.stream().filter(e -> e.getId() == eventId).findFirst();
		if (event.isPresent()) {
			events.remove(event.get());
			event.get().getVisitors().remove(this);
		}
	}

}

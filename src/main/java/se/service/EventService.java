package se.service;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import se.model.Event;

@Service
public interface EventService {
	
	List<Event> getUserEventsPageByFilter(long userId, int page, int size, Predicate<Event> predicate);
	List<Event> getOrganizerEventsPageByFilter(long id, int page, int size, Predicate<Event> predicate);
	List<Event> getAllEvents(int page, int size, Predicate<Event> predicate);
	void createEvent(Event event);
	void addEventForVisitor(long visitorId, long eventId);
	void updateEvent(long organizerId, Event event);
	void deleteEvent(long eventId, long organizerId);

}

package se.service.implementation;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import se.exception.EventAlreadyExistsException;
import se.exception.EventNotFoundException;
import se.exception.UnauthorizedOperationException;
import se.exception.UserNotFoundException;
import se.model.Event;
import se.model.Visitor;
import se.repository.EventRepository;
import se.repository.VisitorRepository;
import se.service.EventService;

@Component
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	VisitorRepository visitorRepository;

	@Override
	@Transactional
	public List<Event> getUserEventsPageByFilter(long userId, int page, int size, Predicate<Event> predicate) {
		List<Event> events = null;
		try (Stream<Event> stream = eventRepository.findByVisitorsId(userId)) {
			if (predicate == null) {
				events = stream.skip((page - 1) * size).limit(size).toList();
			} else {
				events = stream.filter(x -> predicate.test(x)).skip((page - 1) * size).limit(size).toList();
			}
		} catch (Exception e) {
			return null;
		}
		return events;
	}

	@Override
	@Transactional
	public List<Event> getOrganizerEventsPageByFilter(long id, int page, int size, Predicate<Event> predicate) {
		List<Event> events = null;
		try (Stream<Event> stream = eventRepository.findByOrganizerId(id)) {
			if (predicate == null) {
				events = stream.skip((page - 1) * size).limit(size).toList();
			} else {
				events = stream.filter(x -> predicate.test(x)).skip((page - 1) * size).limit(size).toList();
			}
		} catch (Exception e) {
			return null;
		}
		return events;
	}

	@Override
	@Transactional
	public void createEvent(Event event) {
		try {
			eventRepository.saveAndFlush(event);
		} catch (DataIntegrityViolationException e) {
			throw new EventAlreadyExistsException();
		}

	}

	@Override
	@Transactional
	public void addEventForVisitor(long visitorId, long eventId) {

		Visitor visitor = visitorRepository.findById(visitorId).orElse(null);
		if (visitor == null)
			throw new UserNotFoundException();

		Event event = eventRepository.findById(eventId).get();
		if (event == null)
			throw new EventNotFoundException();

		visitor.addEvent(event);
		visitorRepository.save(visitor);
		eventRepository.save(event);

	}

	@Override
	@Transactional
	public void updateEvent(long organizerId, Event event) {
		Event eventDb = eventRepository.findById(event.getId()).orElse(null);
		if (eventDb == null)
			throw new EventNotFoundException();
		if (eventDb.getOrganizer().getId() != organizerId)
			throw new UnauthorizedOperationException();

		eventDb.setDate(event.getDate());
		eventDb.setPlace(event.getPlace());
		eventDb.setTickets(event.getTickets());

		eventRepository.save(eventDb);

	}

	@Override
	@Transactional
	public void deleteEvent(long eventId, long organizerId) {

		Event event = eventRepository.findById(eventId).orElse(null);
		if (event == null)
			return;
		else if (event.getOrganizerid() != organizerId)
			throw new UnauthorizedOperationException();

		Set<Visitor> set = ConcurrentHashMap.newKeySet();
		set.addAll(event.getVisitors());
		for (Visitor visitor : set) {
			visitor.removeEvent(eventId);
			visitorRepository.save(visitor);
		}
		eventRepository.deleteById(eventId);

	}

	@Override
	public List<Event> getAllEvents(int page, int size, Predicate<Event> predicate) {
		return eventRepository.findAll().stream().filter(x -> predicate.test(x)).skip(size * (page - 1)).limit(size)
				.toList();

	}

}

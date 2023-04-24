package se.service.implementation;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import se.exception.OrganizerAlreadyExistsException;
import se.exception.UserNotFoundException;
import se.model.Event;
import se.model.Organizer;
import se.model.User;
import se.model.Visitor;
import se.repository.UserRepository;
import se.repository.VerificationTokenRepository;
import se.service.EventService;
import se.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	private EventService eventService;

	@Override
	@Transactional
	public void deleteUser(long id, boolean organizer) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			if (!organizer && user instanceof Visitor) {
				tokenRepository.deleteById(id);
			} else if (organizer && user instanceof Organizer) {

				Set<Event> set = ConcurrentHashMap.newKeySet();
				set.addAll(((Organizer) user).getEvents());
				for (Event event : set) {
					eventService.deleteEvent(event.getId(), id);
				}
			}

			userRepository.deleteById(id);
		}
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		User userDb = userRepository.findById(user.getId()).orElse(null);
		if (userDb == null)
			throw new UserNotFoundException();

		userDb.setEmail(user.getEmail());
		userDb.setContacts(user.getContacts());
		userRepository.save(userDb);
	}

	@Override
	public User getUser(long id, boolean organizer) {
		User userDb = userRepository.findById(id).orElse(null);
		if (userDb == null)
			throw new UserNotFoundException();
		if ((!organizer && userDb instanceof Visitor) || (organizer && userDb instanceof Organizer)) {
			return userDb;
		}
		return null;

	}

	@Override
	@Transactional
	public Organizer createOrganizer(Organizer organizer) {
		try {
			User user = userRepository.save(organizer);
			return (Organizer) user;

		} catch (DataIntegrityViolationException e) {
			throw new OrganizerAlreadyExistsException();
		}

	}

}

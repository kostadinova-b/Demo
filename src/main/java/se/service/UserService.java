package se.service;

import org.springframework.stereotype.Service;

import se.model.Organizer;
import se.model.User;

@Service
public interface UserService {
	
	void deleteUser(long id, boolean organizer);
	void updateUser(User user);
	User getUser(long id, boolean organizer);
	Organizer createOrganizer(Organizer organizer);
	
}

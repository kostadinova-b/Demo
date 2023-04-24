package se.controller;

import java.sql.Date;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.details.CustomUserDetails;
import se.model.Event;
import se.service.EventService;

@RestController
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private EventService eventService;

	@GetMapping
	public List<Event> getUserEvents(@AuthenticationPrincipal CustomUserDetails user,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String filter) {

		Predicate<Event> predicate = null;
		if (filter.equals("upcoming")) {
			predicate = (Event event) -> {
				return event.getDate().compareTo(new Date(System.currentTimeMillis())) > 0;
			};
		}

		if (filter.equals("past")) {
			predicate = (Event event) -> {
				return event.getDate().compareTo(new Date(System.currentTimeMillis())) < 0;
			};
		}

		if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + "organizer"))) {
			return eventService.getOrganizerEventsPageByFilter(user.getUserId(), page, size, predicate);
		} else {
			return eventService.getUserEventsPageByFilter(user.getUserId(), page, size, predicate);
		}

	}

	@GetMapping("/all")
	public List<Event> getEvents(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String filter) {

		Predicate<Event> predicate = null;
		if (filter.equals("upcoming")) {
			predicate = (Event event) -> {
				return event.getDate().compareTo(new Date(System.currentTimeMillis())) > 0;
			};
		}

		if (filter.equals("past")) {
			predicate = (Event event) -> {
				return event.getDate().compareTo(new Date(System.currentTimeMillis())) < 0;
			};
		}

		return eventService.getAllEvents(page, size, predicate);

	}

}

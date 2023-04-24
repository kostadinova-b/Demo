package se.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.details.CustomUserDetails;
import se.model.Event;
import se.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping("/{eventId}")
	public void addEventForVisitor(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("eventId") long eventId) {
		eventService.addEventForVisitor(user.getUserId(), eventId);
	}

	@PostMapping
	public void createEvent(@AuthenticationPrincipal CustomUserDetails user, @RequestBody Event event) {
		event.setOrganizerid(user.getUserId());
		eventService.createEvent(event);

	}
	
	@PutMapping("/{eventId}")
	public void updateEvent(@PathVariable("eventId") long eventId, @AuthenticationPrincipal CustomUserDetails user,
			@RequestBody Event event) {
		eventService.updateEvent(user.getUserId(), event);
	}

	@DeleteMapping("/{eventId}")
	public void deleteEvent(@PathVariable("eventId") long eventId, @AuthenticationPrincipal CustomUserDetails user) {
		eventService.deleteEvent(eventId, user.getUserId());
	}

}

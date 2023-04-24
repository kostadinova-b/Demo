package se.repository;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import se.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	Stream<Event> findByOrganizerId(Long id);
	Stream<Event> findByVisitorsId(Long id);
	Stream<Event> findByOrganizerIdOrderByDateDesc(Long id);
	Stream<Event> findByVisitorsIdOrderByDateDesc(Long id);
	
	@Query(value = "select * from events where id in (select eventid from visitors_events where visitorid = ?1)", nativeQuery = true)
	Collection<Event> findAllEventsByVisitorId(Long id);
	
	@Modifying
	@Query(value = "insert into visitors_events values(?1,?2)", nativeQuery = true)
	void addEvent(Long userId, Long eventId);

	@Query(value = "select * from events where id not in (select eventid from visitors_events where visitorid = ?1", nativeQuery = true)
	Collection<Event> findAllUnvisitedEventsByVisitorId(Long id);
	
	
}

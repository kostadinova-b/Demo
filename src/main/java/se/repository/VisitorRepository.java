package se.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import se.model.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>{
	
	List<Visitor> findVisitorsByEventsId(Long eventId);

}

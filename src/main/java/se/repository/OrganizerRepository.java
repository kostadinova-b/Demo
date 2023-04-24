package se.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.model.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long>{

}

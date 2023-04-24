package se.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.model.ImageData;

@Repository
public interface ImageRepository extends JpaRepository<ImageData, Long>{
	
	Optional<ImageData> findByLocation(String location);
	Set<ImageData> findByEventId(Long id);
	Set<ImageData> findByUserId(Long id);
	Set<ImageData> findByUserIdAndEventId(Long id, Long eventId);
	

}

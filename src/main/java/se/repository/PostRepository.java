package se.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	Stream<Post> findByEventIdOrderByPostTimeDesc(Long id);
	
}

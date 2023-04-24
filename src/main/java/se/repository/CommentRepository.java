package se.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import se.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostid(Long postid);
	Stream<Comment> findByPostIdOrderByPostTime(long id);
}

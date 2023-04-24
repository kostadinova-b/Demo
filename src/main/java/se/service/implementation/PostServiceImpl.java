package se.service.implementation;

import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import se.exception.PostDeniedException;
import se.exception.UnauthorizedOperationException;
import se.model.Event;
import se.model.Post;
import se.repository.EventRepository;
import se.repository.PostRepository;
import se.service.PostService;

@Component
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;
	@Autowired
	EventRepository eventRepository;

	@Override
	@Transactional
	public Post createPost(long organizerId, Post post) {
		Event event = eventRepository.findById(post.getEventid()).orElse(null);
		if(event != null && event.getOrganizerid() != organizerId)
			throw new UnauthorizedOperationException();
		try {
			postRepository.saveAndFlush(post);
		} catch (DataIntegrityViolationException e) {
			throw new PostDeniedException(post.getEvent().getName());
		}

		return post;
	}

	@Override
	@Transactional
	public void deletePost(long organizerId, long eventId,  long postId) {
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event != null && event.getOrganizerid() != organizerId)
			throw new UnauthorizedOperationException();
		postRepository.deleteById(postId);
	}

	@Override
	@Transactional
	public List<Post> getEventsPostPageById(long id, int page, int size) {	
		try (Stream<Post> postsStream = postRepository.findByEventIdOrderByPostTimeDesc(id)) {
			return postsStream.skip((page - 1) * size).limit(size).toList();
		} catch (Exception e) {
			return null;
		}
	}

}

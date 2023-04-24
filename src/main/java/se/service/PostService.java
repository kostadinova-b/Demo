package se.service;

import java.util.List;

import org.springframework.stereotype.Service;

import se.model.Post;

@Service
public interface PostService {
	
	Post createPost(long organizerId, Post post);
	void deletePost(long organizerId, long eventId, long postId);
	List<Post> getEventsPostPageById(long id, int page, int size);

}

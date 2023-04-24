package se.service;

import java.util.List;

import org.springframework.stereotype.Service;

import se.model.Comment;

@Service
public interface CommentService {
	
	void createComment(long userId, Comment comment);
	void deleteCommentById(long userId, long commentId);
	List<Comment> getPostComments(long postId, int page, int size);

}

package se.service.implementation;

import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import se.exception.CommentDeniedException;
import se.exception.ResourceNotFoundException;
import se.exception.UnauthorizedOperationException;
import se.model.Comment;
import se.model.Post;
import se.repository.CommentRepository;
import se.repository.PostRepository;
import se.service.CommentService;

@Component
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	@Transactional
	private void saveComment(Comment comment) {
		try {
			commentRepository.save(comment);
		} catch (DataIntegrityViolationException e) {
			throw new CommentDeniedException();
		}
	}

	@Override
	@Transactional
	public void createComment(long userId, Comment comment) {

		Post post = postRepository.findById(comment.getPostid()).orElse(null);
		if (post == null)
			throw new CommentDeniedException();
		if (post.getEvent().getOrganizerid() != userId && post.getEvent().getVisitors().stream()
				.filter(x -> x.getId() == userId).findFirst().orElse(null) == null)
			throw new ResourceNotFoundException();

		saveComment(comment);

	}

	@Override
	@Transactional
	public void deleteCommentById(long userId, long commentId) {
		Comment comment = commentRepository.findById(commentId).orElse(null);
		if (comment != null) {
			if (comment.getUserid() != userId) {
				throw new UnauthorizedOperationException();
			}
			commentRepository.deleteById(comment.getId());
		}

	}

	@Override
	@Transactional
	public List<Comment> getPostComments(long postId, int page, int size) {
		try (Stream<Comment> commentStream = commentRepository.findByPostIdOrderByPostTime(postId)) {
			return commentStream.skip((page - 1) * size).limit(size).toList();
		} catch (Exception e) {
			return null;
		}
	}

}

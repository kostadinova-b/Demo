package se.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.details.CustomUserDetails;
import se.model.Comment;
import se.model.Post;
import se.service.CommentService;
import se.service.PostService;

@RestController
@RequestMapping("/events")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/{eventId}/posts")
	public List<Post> getEventPostsById(@PathVariable long eventId, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		return postService.getEventsPostPageById(eventId, page, size);
	}

	@PostMapping("/{eventId}/posts")
	public void createPostForEvent(@AuthenticationPrincipal CustomUserDetails user, @PathVariable long eventId,
			@RequestBody Post post) {
		post.setEventid(eventId);
		post.setPostTime(new Timestamp(System.currentTimeMillis()));
		postService.createPost(user.getUserId(), post);
	}

	@DeleteMapping("/{eventId}/posts/{postId}")
	public void deletePostFromEvent(@AuthenticationPrincipal CustomUserDetails user,
			@PathVariable("eventId") long eventId, @PathVariable("postId") long postId) {
		postService.deletePost(user.getUserId(), eventId, postId);
	}

	@GetMapping("/{eventId}/posts/{postId}/comments")
	public List<Comment> getPostComments(@PathVariable("eventId") long eventId, @PathVariable("postId") long postId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
		return commentService.getPostComments(postId, page, size);
	}

	@PostMapping("/{eventId}/posts/{postId}/comments")
	public void createComment(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("postId") long postId,
			@RequestBody Comment comment) {
		comment.setPostTime(new Timestamp(System.currentTimeMillis()));
		comment.setUserid(user.getUserId());
		comment.setPostid(postId);
		commentService.createComment(postId, comment);
	}

	@DeleteMapping("/{eventId}/posts/{postId}/comments/{id}")
	public void deleteComment(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("id") long id) {
		commentService.deleteCommentById(user.getUserId(), id);
	}
}

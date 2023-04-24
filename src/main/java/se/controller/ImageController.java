package se.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import se.details.CustomUserDetails;
import se.exception.ResourceLoadingException;
import se.exception.ResourceLocationGenerationException;
import se.model.ImageData;
import se.service.ImageService;

@RestController
@RequestMapping("/events")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@GetMapping(value = "{eventId}/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageWithMediaType(@PathVariable Long id) {

		String imgPath = imageService.getImageLocationById(id);
		byte[] imgData = null;
		try {
			imgData = Files.readAllBytes(Paths.get(imgPath));
		} catch (IOException e) {
			throw new ResourceLoadingException();
		}

		return imgData;

	}


	@PostMapping("/{eventId}/images")
	public void uploadImages(@AuthenticationPrincipal CustomUserDetails user,
			@RequestParam("images") MultipartFile[] files, @PathVariable("eventId") long eventId) {
		Arrays.asList(files).stream().map(file -> {
			try {
				return new ImageData(eventId, user.getUserId(), file.getBytes());
			} catch (ResourceLocationGenerationException | IOException e) {
				return null;
			}
		}).filter(img -> img != null).forEach(img -> imageService.saveImage(img));
	}
	
	@DeleteMapping("/{eventId}/images")
	public void deleteUserImages(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("eventId") long eventId) {
		imageService.deleteEventImagesByUserId(user.getUserId(), eventId);
	}
	
	@DeleteMapping("/{eventId}/images/{imgId}")
	public void deleteImage(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("imgId") long imgId) {
		imageService.deleteImage(imgId, user.getUserId());
	}
	

}

package se.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import se.model.ImageData;

@Service
public interface ImageService {
	
	void saveImage(ImageData imageData);
	void uploadImages(List<ImageData> images);
	void deleteImage(Long imgId, Long userId);
	void deleteEventImagesByUserId(Long id, Long eventId);
	Set<ImageData> getAllImagesByEventId(Long id);
	Set<ImageData> getAllImagesByUserId(Long id);
	String getImageLocationById(Long id);
	
	

}

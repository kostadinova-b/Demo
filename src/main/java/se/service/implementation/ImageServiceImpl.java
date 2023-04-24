package se.service.implementation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import se.exception.ResourceSavingException;
import se.exception.UnauthorizedOperationException;
import se.exception.ResourceNotFoundException;
import se.model.ImageData;
import se.repository.FileSystemImageRepository;
import se.repository.ImageRepository;
import se.service.ImageService;

@Component
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private FileSystemImageRepository fileSystemImageRepository;

	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	@Transactional
	public void saveImage(ImageData imageData) {
		try {
			imageRepository.saveAndFlush(imageData);
			fileSystemImageRepository.saveImage(imageData.getImgData(), imageData.getLocation());
		} catch (IOException e) {
			throw new ResourceSavingException();
		}
	}

	@Override
	public Set<ImageData> getAllImagesByEventId(Long id) {
		return imageRepository.findByEventId(id);
	}

	@Override
	public Set<ImageData> getAllImagesByUserId(Long id) {
		return imageRepository.findByUserId(id);
	}

	@Override
	public void deleteImage(Long imgId, Long userId) {

		ImageData imageData = imageRepository.findById(imgId).orElse(null);
		if (imageData != null) {
			if (imageData.getUserid() != userId || imageData.getEvent().getOrganizerid() != userId) {
				throw new UnauthorizedOperationException();
			}
			String location = imageData.getLocation();
			taskExecutor.execute(() -> fileSystemImageRepository.deleteImage(location));
			imageRepository.deleteById(imageData.getId());

		}

	}

	@Override
	public void deleteEventImagesByUserId(Long id, Long eventId) {
		Set<ImageData> imgs = imageRepository.findByUserIdAndEventId(id, eventId);
		taskExecutor.execute(() -> imgs.forEach(x -> fileSystemImageRepository.deleteImage(x.getLocation())));
		imageRepository.deleteAll(imgs);

	}

	@Override
	public String getImageLocationById(Long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException()).getLocation();
	}

	@Override
	@Transactional
	public void uploadImages(List<ImageData> images) {

		imageRepository.saveAll(images);
		images.forEach((imageData) -> {
			try {
				fileSystemImageRepository.saveImage(imageData.getImgData(), imageData.getLocation());
			} catch (IOException e) {
				throw new ResourceSavingException();
			}
		});

	}

}

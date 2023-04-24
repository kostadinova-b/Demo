package se.repository;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public class FileSystemImageRepository {

	
	public void saveImage(byte[] img, String location) throws IOException {
		Path path = Paths.get(location);
		Files.createDirectories(path);
		BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(img));
		ImageIO.write(bimg, "jpg", path.toFile());

	}

	@Async
	public void deleteImage(String absolutePath) {
		Path path = Paths.get(absolutePath);
		if (!Files.isDirectory(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

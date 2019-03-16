package com.example.fileUpload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.example.fileUpload.exception.FileDownloadException;
import com.example.fileUpload.exception.FileStorageException;
import com.example.fileUpload.property.FileStorageProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageProperty fileStorageProperty) {
		// get file storage location from file storage property
		this.fileStorageLocation = Paths.get(fileStorageProperty.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			// Create the upload directory.
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("upload directory cannot be created", ex);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			//Check if fileName contains illegal characters.
			if(fileName.contains("..")) {
				throw new FileStorageException("Invalid path sequence");
			}
            // Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		}catch(IOException ex) {
			throw new FileStorageException("Could not store file " + fileName, ex);
		}
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}else {
				throw new FileDownloadException("File not found");
			}
		}catch(MalformedURLException ex) {
			throw new FileDownloadException("File not found", ex);
		}
	}
}

package com.example.fileUpload.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

// This configuration bind all configuration in application.properties 
// with prefix "file" to POJO class
@ConfigurationProperties(prefix="file")
public class FileStorageProperty {
	private String uploadDir;
	
	public String getUploadDir() {
		return this.uploadDir;
	}
	
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}

package com.example.fileUpload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.fileUpload.property.FileStorageProperty;

// To add configuration feature to the main App

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperty.class
})
public class MultipartFileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipartFileUploadApplication.class, args);
	}

}

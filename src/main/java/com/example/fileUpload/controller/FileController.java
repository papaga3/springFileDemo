package com.example.fileUpload.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.fileUpload.service.FileStorageService;
import com.example.fileUpload.payload.UploadFileResponse;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
@RestController
public class FileController {
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		
		//Build an URL for download the file.
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
		return new UploadFileResponse(fileName, fileDownloadUri, 
				file.getContentType(), file.getSize());
	}
	
	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFile(
			@RequestParam("files") MultipartFile[] files){
		List<UploadFileResponse> returnList = new ArrayList<UploadFileResponse>();
		for(MultipartFile f : files) {
			returnList.add(uploadFile(f));
		}
		return returnList;
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, 
			HttpServletRequest request){
		//load file as resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		
		String contentType = null;
		try {
			contentType = request.getServletContext().
					getMimeType(resource.getFile().getAbsolutePath());
		}catch(IOException ex) {
			System.out.println("Could not determine file type: ");
		}
		
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						"attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}

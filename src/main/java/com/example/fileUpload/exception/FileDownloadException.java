package com.example.fileUpload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// This class is used to throw exception when downloading file.
// The exception is file not found http 404

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileDownloadException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileDownloadException(String message) {
		super(message);
	}
	
	public FileDownloadException(String message, Throwable cause) {
		super(message, cause);
	}
}

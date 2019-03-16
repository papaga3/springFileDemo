package com.example.fileUpload.exception;

// This class is the exception to throw when something is wrong 
// with writing file to the file system.
public class FileStorageException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileStorageException(String message) {
		super(message);
	}
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}

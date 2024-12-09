package com.nimap.exception;

public class ResourceNotFoundException extends RuntimeException{

	private String message;
	
	public ResourceNotFoundException(String message) {
		this.message=message;
	}
}

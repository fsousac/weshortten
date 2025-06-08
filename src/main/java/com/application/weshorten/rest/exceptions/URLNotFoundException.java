package com.application.weshorten.rest.exceptions;

public class URLNotFoundException extends RuntimeException {
	public URLNotFoundException(String message) {
		super(message);
	}
}

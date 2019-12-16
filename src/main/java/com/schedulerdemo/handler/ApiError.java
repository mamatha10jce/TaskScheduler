package com.schedulerdemo.handler;

import org.springframework.http.HttpStatus;

/**
 * @author Mamatha Dec 12, 2019 9:03:50 PM
 * ApiError.java
 */
public class ApiError {
	HttpStatus status;
	String title;
	String description;

	public ApiError() {
	}

	public ApiError(HttpStatus status, String title, String description) {
		this.status = status;
		this.title = title;
		this.description = description;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

package com.example.springboot.demo.exception;

import java.util.Date;

public class ErrorDetails {
	
	private Date timestamps;
	private String message;
	private String details;
	public Date getTimestamps() {
		return timestamps;
	}
	public ErrorDetails(Date timestamps, String message, String details) {
		super();
		this.timestamps = timestamps;
		this.message = message;
		this.details = details;
	}
	public void setTimestamps(Date timestamps) {
		this.timestamps = timestamps;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}

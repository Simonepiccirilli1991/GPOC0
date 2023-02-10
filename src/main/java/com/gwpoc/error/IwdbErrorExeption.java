package com.gwpoc.error;

public class IwdbErrorExeption extends RuntimeException{

	private String message;
	private String causa;
	
	public IwdbErrorExeption(String message, String causa) {
		super();
		this.message = message;
		this.causa = causa;
	}
	public String getMessage() {
		return message;
	}
	public String getCausa() {
		return causa;
	}
	
	
	
}

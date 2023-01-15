package com.gwpoc.error;

public class AppException extends RuntimeException{

	
	private String errorCode;

	
	public AppException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}

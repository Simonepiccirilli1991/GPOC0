package com.gwpoc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gwpoc.error.AppException;
import com.gwpoc.error.BaseError;


@RestControllerAdvice
public class ErrorController {

	public static final String LOGIC_PREFIX = "gwsi0.logic.";
	private static final String GENERIC = "gwgp0.generic";
	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<BaseError> actionError(String errorCode){
		
		
		ResponseEntity<BaseError> response = new ResponseEntity<BaseError>(errorMapper(errorCode).getHttpStatus());
		response.getBody().setChiamante(GENERIC);
		response.getBody().setErrorMsg(errorMapper(errorCode).getErrCode());
		response.getBody().setErrorTp(errorCode);
		response.getBody().setHttpS(errorMapper(errorCode).getHttpStatus());
		return response; 
	}
	
	
	
	
	private static ErrorMapperDTO errorMapper(String errId) {
		if(errId == null) errId = "EMPTY";
		String errCode = null;
		String logicErrId = null;
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;

		switch (errId) {
			case "ERKO-02": case "ERKO-03":
			case "ERKO-04":			httpStatus = HttpStatus.UNAUTHORIZED;	logicErrId = "Invalid data provided";			break;
			
			case "ERKO-10":         httpStatus = HttpStatus.UNAUTHORIZED;	logicErrId = "U need to login first";			break;
			default:
				errCode = GENERIC;
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				break;

			
		}

		if(logicErrId != null) errCode = LOGIC_PREFIX + logicErrId;

		return new ErrorMapperDTO(errCode, httpStatus);
	}
}
class ErrorMapperDTO {
	private final String errCode;
	private final HttpStatus httpStatus;
	
	public ErrorMapperDTO(String errCode, HttpStatus httpStatus) {
		this.errCode = errCode;
		this.httpStatus = httpStatus;
	}

	public String getErrCode() { return errCode; }
	public HttpStatus getHttpStatus() { return httpStatus; }

}

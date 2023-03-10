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
	public ResponseEntity<BaseError> actionError(AppException ex){
		
		
		BaseError response = new BaseError();
		
		ErrorMapperDTO dto = errorMapper(ex.getErrorCode());
		response.setErrorMsg(dto.getErrCode());
		response.setErrorTp(ex.getErrorCode());
		response.setHttpS(dto.getHttpStatus());
		return new ResponseEntity<>(response, dto.getHttpStatus());
	}
	
	
	
	
	private static ErrorMapperDTO errorMapper(String errId) {
		if(errId == null) errId = "EMPTY";
		String errCode = null;
		String logicErrId = null;
		HttpStatus httpStatus = null;

		switch (errId) {
			case "ERKO-00":     	httpStatus = HttpStatus.BAD_REQUEST;logicErrId = "Invalid request, missing parameter";			break;
			case "ERKO-01":			httpStatus = HttpStatus.CONFLICT;	logicErrId = "Error on saving data , retry after";			break;
			case "ERKO-02":         httpStatus = HttpStatus.CONFLICT;   logicErrId = "Error on retriving data , retry after";		break;
			case "ERKO-03":			httpStatus = HttpStatus.FAILED_DEPENDENCY;logicErrId = "Error on updating data";		   		break;
			
			case "ERKO-04":			httpStatus = HttpStatus.UNAUTHORIZED;	logicErrId = "Invalid data provided";					break;
			case "Error inserting account":  httpStatus = HttpStatus.UNAUTHORIZED;	logicErrId = "Need to register first";			break;
			case "ERKO-10":         httpStatus = HttpStatus.UNAUTHORIZED;	logicErrId = "U need to login first";					break;
			default:
				errCode = GENERIC;
				logicErrId = "Generic Error";
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				break;

			
		}

		errCode = LOGIC_PREFIX + logicErrId;

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

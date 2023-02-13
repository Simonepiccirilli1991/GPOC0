package com.gwpoc.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.gwpoc.error.AppException;
import com.gwpoc.model.action.BaseActionCommand;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.service.pin.CertifyMailService;
import com.gwpoc.service.pin.CheckPinService;



@Component
@Scope("prototype")
public class PinCommand extends BaseActionCommand<PinRequest, PinResponse>{

	Logger logger = LoggerFactory.getLogger(PinCommand.class);
	
	public PinCommand(PinRequest iRequest, HttpHeaders header) {
		super(iRequest, header);
	}

	public ResponseEntity<PinResponse>  doExcute() throws Exception{
		
		logger.info("API :PinCommand - raw request before command execution: {}", iRequest);
		
		switch (iRequest.getAction()) {
		// chiama alla status di wiam
			case CHECKPIN:
				if(ObjectUtils.isEmpty(iRequest.getBt()) && ObjectUtils.isEmpty(iRequest.getPin()))
					throw new AppException("invalid request");
				PinResponse response = super.getResponse(CheckPinService.class);
				
				return new ResponseEntity<>(response, response.getHttpHeaders(),HttpStatus.OK);
			
			case CERTIFYMAIL:
				if(ObjectUtils.isEmpty(iRequest.getBt()) || ObjectUtils.isEmpty(iRequest.getOtp()))
					throw new AppException("invalid request");
				
				PinResponse  iResponse = super.getResponse(CertifyMailService.class);
				
				return new ResponseEntity<>(iResponse,HttpStatus.OK);
		default:
			throw new AppException("");
		}
	}
 
	
	
}

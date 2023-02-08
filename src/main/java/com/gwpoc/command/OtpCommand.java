package com.gwpoc.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.gwpoc.error.AppException;
import com.gwpoc.model.action.BaseActionCommand;
import com.gwpoc.model.request.OtpRequest;
import com.gwpoc.model.response.OtpResponse;
import com.gwpoc.service.otp.CheckOtpService;
import com.gwpoc.service.otp.GenerateOtpService;

@Component
@Scope("prototype")
public class OtpCommand extends BaseActionCommand<OtpRequest, OtpResponse>{

	Logger logger = LoggerFactory.getLogger(OtpCommand.class);
	
	public OtpCommand(OtpRequest iRequest, HttpHeaders httpHeaders) {
		super(iRequest, httpHeaders);
		// TODO Auto-generated constructor stub
	}
	
	public OtpResponse doExcute() throws Exception{
		
		logger.info("API :OtpCommand - raw request before command execution: {}", iRequest);
		
		switch (iRequest.getAction()) {
		// chiama alla status di wiam
			case SENDOTP:
				if(ObjectUtils.isEmpty(iRequest.getBt()) && ObjectUtils.isEmpty(iRequest.getEmail()))
					throw new AppException("invalid request");
				return super.getResponse(GenerateOtpService.class);
			
			case CHECKOTP:
				if(ObjectUtils.isEmpty(iRequest.getBt()) || ObjectUtils.isEmpty(iRequest.getOtp()))
					throw new AppException("invalid request");
				return super.getResponse(CheckOtpService.class);
		default:
			throw new AppException("");
		}
	}

}

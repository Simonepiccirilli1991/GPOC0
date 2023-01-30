package com.gwpoc.client;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
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

	public PinCommand(PinRequest iRequest, HttpHeaders header) {
		super(iRequest, header);
	}

	public PinResponse doExcute() throws Exception{
		switch (iRequest.getAction()) {
		// chiama alla status di wiam
			case CHECKPIN:
				if(ObjectUtils.isEmpty(iRequest.getBt()) && ObjectUtils.isEmpty(iRequest.getPin()))
					throw new AppException("invalid request");
				return super.getResponse(CheckPinService.class);
			
			case CERTIFYMAIL:
				if(ObjectUtils.isEmpty(iRequest.getBt()) || ObjectUtils.isEmpty(iRequest.getOtp()))
					throw new AppException("invalid request");
				return super.getResponse(CertifyMailService.class);
		default:
			throw new AppException("");
		}
	}
 
	
	
}

package com.gwpoc.service.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.OtpvPushClient;
import com.gwpoc.model.request.PushRequest;
import com.gwpoc.model.response.PushResponse;
import com.gwpoc.service.pin.CertifyMailService;

@Service
public class PushService {

	@Autowired
	OtpvPushClient pushCLient;
	
	Logger logger = LoggerFactory.getLogger(PushService.class);
	
	
//-------------- SEND PUSH ---------------------------------//
	public PushResponse sendPush(String bt) {
		
		logger.info("API :PushService - send - START with raw request: {}", bt);
		
		PushRequest request = new PushRequest();
		request.setBt(bt);
		request.setBancaId("bandaId");//TODO poi implementare logica dei banca Id
		
		PushResponse response = pushCLient.sendPush(request);
		
		logger.info("API :PushService - send -  END with response: {}", response);
		
		return response;
	}
	
//--------------- ACCEPT PUSH ------------------------------------------//
	public PushResponse acceptPush(String bt) {
		
		logger.info("API :PushService - accept - START with raw request: {}", bt);
		
		PushRequest request = new PushRequest();
		request.setBt(bt);
		request.setBancaId("bandaId");//TODO poi implementare logica dei banca Id
		
		PushResponse response = pushCLient.acceptPush(request);
		
		logger.info("API :PushService - accept -  END with response: {}", response);
		
		return response;
	}
	
//--------GET status PUSH POLLING---------------------------------------------------//
	public PushResponse getStatusPush(PushRequest request) {
		
		logger.info("API :PushService - get status - START with raw request: {}", request);
		
		PushResponse response = new PushResponse();
		while(true) {
			
			response = pushCLient.getStatusPush(request);
			if(! response.getStatus().equals("pending"))
				break;
		}
		
		logger.info("API :PushService - get status -  END with response: {}", response);
		
		return response;
	}
	
}

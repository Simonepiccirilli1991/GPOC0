package com.gwpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.CachClient;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;

@Service
public class SessionService {

	@Autowired
	CachClient sessCLient;
	
	Logger logger = LoggerFactory.getLogger(SessionService.class);
	
	public SessionResponse createSession(SessionRequest request) {
		logger.info("API :SessionService - create -  START with raw request: {}", request);
		
		SessionResponse response = sessCLient.createSession(request);
		
		logger.info("API :SessionService - create -  END response : {}", response);
		return response;
	}
	
	public SessionResponse getSession(SessionRequest request) {
		logger.info("API :SessionService - get -  START with raw request: {}", request);
		SessionResponse response = sessCLient.getSession(request);
		
		logger.info("API :SessionService - get -  END response : {}", response);
		return response;
	}
	
	public SessionResponse update(SessionRequest request) {
		logger.info("API :SessionService - update -  START with raw request: {}", request);
		SessionResponse response = sessCLient.getSession(request);
		
		logger.info("API :SessionService - update -  END response : {}", response);
		return response;
	}
}

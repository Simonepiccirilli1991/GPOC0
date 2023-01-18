package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.CachClient;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;

@Service
public class SessionService {

	@Autowired
	CachClient sessCLient;
	
	public SessionResponse createSession(SessionRequest request) {
		
		SessionResponse response = sessCLient.createSession(request);
		
		return response;
	}
	
	public SessionResponse getSession(SessionRequest reuqest) {
		
		SessionResponse response = sessCLient.getSession(reuqest);
		
		return response;
	}
	
}

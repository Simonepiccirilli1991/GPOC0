package com.gwpoc.service.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.OtpvPushClient;
import com.gwpoc.model.request.PushRequest;
import com.gwpoc.model.response.PushResponse;

@Service
public class PushService {

	@Autowired
	OtpvPushClient pushCLient;
	
//-------------- SEND PUSH ---------------------------------//
	public PushResponse sendPush(String bt) {
		
		PushRequest request = new PushRequest();
		request.setBt(bt);
		request.setBancaId("bandaId");//TODO poi implementare logica dei banca Id
		
		PushResponse response = pushCLient.sendPush(request);
		
		return response;
	}
	
//--------------- ACCEPT PUSH ------------------------------------------//
	public PushResponse acceptPush(String bt) {
		
		PushRequest request = new PushRequest();
		request.setBt(bt);
		request.setBancaId("bandaId");//TODO poi implementare logica dei banca Id
		
		PushResponse response = pushCLient.acceptPush(request);
		
		return response;
	}
	
//--------GET status PUSH POLLING---------------------------------------------------//
	public PushResponse getStatusPush(PushRequest request) {
		
		PushResponse response = new PushResponse();
		while(true) {
			
			response = pushCLient.getStatusPush(request);
			if(! response.getStatus().equals("pending"))
				break;
		}
		
		return response;
	}
	
}

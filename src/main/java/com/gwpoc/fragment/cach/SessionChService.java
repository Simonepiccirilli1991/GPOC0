package com.gwpoc.fragment.cach;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.gwpoc.Util.ConstEnum;
import com.gwpoc.client.CachClient;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;

public class SessionChService {

	@Autowired
	CachClient cachClient;
	
	public Boolean checkL2(SessionRequest request) {
		
		Optional<SessionResponse> response = cachClient.getSession(request);
		
		if(response.isEmpty()) {
			//TODO implementare lancio eccezzione
		}	
			
		// controllo che scope sia L2 se no lancio eccezzione
		if(!Boolean.TRUE.equals(response.get().getScope().equals(ConstEnum.L2.value()))) {
			//sessione non in l2 non lancio eccezzione, setto solo response a valid false
			return false;
		}
		
		return true;
	}
}

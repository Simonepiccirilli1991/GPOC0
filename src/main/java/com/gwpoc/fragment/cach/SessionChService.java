package com.gwpoc.fragment.cach;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.Util.ConstEnum;
import com.gwpoc.client.CachClient;
import com.gwpoc.error.AppException;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;

@Service
public class SessionChService {

	@Autowired
	CachClient cachClient;
	
	public Boolean checkL2(SessionRequest request) {
		
		SessionResponse response = cachClient.getSession(request);
		
		if(!ObjectUtils.isEmpty(response.getNoFound()) && response.getNoFound()) {
			throw new AppException("SSKO-2");
		}	
			
		// controllo che scope sia L2 se no lancio eccezzione
		if(!Boolean.TRUE.equals(response.getScope().equals(ConstEnum.L2.value()))) {
			//sessione non in l2 non lancio eccezzione, setto solo response a valid false
			return false;
		}
		
		return true;
	}
}

package com.gwpoc.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.client.AgtwClient;
import com.gwpoc.error.AppException;
import com.gwpoc.model.action.BaseActionService;
import com.gwpoc.model.request.AuthRequest;
import com.gwpoc.model.response.AuthResponse;

@Service
public class GetAuthService {

	@Autowired
	AgtwClient agtwClient;
	
	
	public AuthResponse generateAuth(AuthRequest iRequest, HttpHeaders headers) {
		
		AuthResponse response = new AuthResponse();
		HttpHeaders header = new HttpHeaders();
		ResponseEntity<Boolean> iResp = agtwClient.createAuth(iRequest);
		
		if(ObjectUtils.isEmpty(iResp.getBody())  || !iResp.getBody())
			throw new AppException("TODO");
		else
			header.add("Authorization", iResp.getHeaders().getFirst("Authorization"));
		
		response.setHttpHeaders(header);
		response.setGenerated(true);
		return response;
	}

	
	
}

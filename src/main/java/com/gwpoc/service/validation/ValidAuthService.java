package com.gwpoc.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gwpoc.client.AgtwClient;
import com.gwpoc.model.request.AuthRequest;
import com.gwpoc.model.response.AuthResponse;

@Service
public class ValidAuthService{

	@Autowired
	AgtwClient agtwClient;
	
	public ResponseEntity<AuthResponse> validateAuthWithData(AuthRequest request, HttpHeaders header) {
		
		AuthResponse response = new AuthResponse();
		// fa cose
		
		return new ResponseEntity<>(response,header,HttpStatus.OK);
	}
}

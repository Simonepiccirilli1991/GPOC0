package com.gwpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.SessionService;

@RestController
public class SessionController {

	@Autowired
	SessionService sessService;
	
	//------- Sessione di sicurezza -------------------------------//
	@PostMapping("sess/create")
	public ResponseEntity<SessionResponse> createSession(@RequestBody SessionRequest request){
		return new ResponseEntity<>(sessService.createSession(request), HttpStatus.OK);
	}
	@PostMapping("sess/get")
	public ResponseEntity<SessionResponse> getSession(@RequestBody SessionRequest request){
		return new ResponseEntity<>(sessService.getSession(request), HttpStatus.OK);
	}
	
	//------------Sessione Applicativa ----------------------------------------------------//
	//TODO da creare
	// creare sessione applicativa dopo consent - sic l2 
}

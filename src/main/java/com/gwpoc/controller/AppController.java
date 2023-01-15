package com.gwpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.UtenteResponse;
import com.gwpoc.service.UtenteService;

@RestController
@RequestMapping("app")
public class AppController {

	@Autowired
	UtenteService utenteService;
	
	@PostMapping("register")
	public ResponseEntity<UtenteResponse> registyUtente(@RequestBody UtenteRequest request){
		
		return new ResponseEntity<>(utenteService.registra(request), HttpStatus.OK);
	}
	
	@PostMapping("update")
	public ResponseEntity<UtenteResponse> updateUtente(@RequestBody UtenteRequest request){
		
		return new ResponseEntity<>(utenteService.update(request), HttpStatus.OK);
	}
	
	@PostMapping("get")
	public ResponseEntity<UtenteResponse> getUtente(@RequestBody String bt){
		return new ResponseEntity<>(utenteService.getUtente(bt),HttpStatus.OK);
	}
}

package com.gwpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwpoc.fragment.model.Ordini;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.request.OrdiniRequest;
import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.AccountResponse;
import com.gwpoc.model.response.UtenteResponse;
import com.gwpoc.service.AccountService;
import com.gwpoc.service.OrdiniService;
import com.gwpoc.service.UtenteService;

@RestController
@RequestMapping("app")
public class AppController {

	@Autowired
	UtenteService utenteService;
	@Autowired
	AccountService accService;
	@Autowired
	OrdiniService ordService;
	
	//utente controller
	@PostMapping("utente/register")
	public ResponseEntity<UtenteResponse> registyUtente(@RequestBody UtenteRequest request){	
		return new ResponseEntity<>(utenteService.registra(request), HttpStatus.OK);
	}
	
	@PostMapping("utente/update")
	public ResponseEntity<UtenteResponse> updateUtente(@RequestBody UtenteRequest request){
		
		return new ResponseEntity<>(utenteService.update(request), HttpStatus.OK);
	}
	
	@PostMapping("/utente/get")
	public ResponseEntity<UtenteResponse> getUtente(@RequestBody String bt){
		return new ResponseEntity<>(utenteService.getUtente(bt),HttpStatus.OK);
	}
	//account controller
	@PostMapping("acc/register")
	public ResponseEntity<AccountResponse> insertAcc(@RequestBody AccountRequest request){
		return new ResponseEntity<>(accService.insertAccount(request),HttpStatus.OK);
	}
	@PostMapping("acc/update")
	public ResponseEntity<AccountResponse> updateAcc(@RequestBody AccountRequest request){
		return new ResponseEntity<>(accService.updateAccount(request),HttpStatus.OK);
	}
	@GetMapping("acc/get/{bt}")
	public ResponseEntity<AccountResponse> insertAcc(@PathVariable ("bt") String bt){
		return new ResponseEntity<>(accService.getAccount(bt),HttpStatus.OK);
	}
	//OrderCOntroller
	@PostMapping("ord/create")
	public ResponseEntity<Ordini> createOrder(@RequestBody OrdiniRequest request){
		return new ResponseEntity<>(ordService.creaOrdine(request), HttpStatus.OK);
	}
	@PostMapping("ord/get")
	public ResponseEntity<Ordini> getOrder(@RequestBody OrdiniRequest request){
		return new ResponseEntity<>(ordService.getOrdine(request), HttpStatus.OK);
	}
}

package com.gwpoc.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwpoc.command.OtpCommand;
import com.gwpoc.command.PinCommand;
import com.gwpoc.model.request.OtpRequest;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.response.OtpResponse;
import com.gwpoc.model.response.PinResponse;

@RestController
@RequestMapping("action")
public class ActionController {

	@Autowired protected BeanFactory beanFactory;
	
	@PostMapping("pin")
	public ResponseEntity<PinResponse> pinServices(@RequestBody PinRequest request) throws Exception {
		return new ResponseEntity<>(beanFactory.getBean(PinCommand.class, request, null).doExcute(), HttpStatus.OK);
	}
	
	@PostMapping("otp")
	public ResponseEntity<OtpResponse> otpServices(@RequestBody OtpRequest request) throws Exception {		
		return  new ResponseEntity<>(beanFactory.getBean(OtpCommand.class, request, null).doExcute(), HttpStatus.OK);
	}
	
	//TODO continuare 
		// chiamata a status prima di tutto.
		// flusso -> registra utente su iwdb , poi salvo anagrafica su ansc
		// chiusa registrazione
		// faccio login checkpin e setto a l1, controllo se mail e certificata se no certifico.
		// dopo certifica o se certificata mando otp mail e setto a l2.
		// controllo se ha gia acc dispo, se no registra dispo.
		// creo token e posso operare.
}

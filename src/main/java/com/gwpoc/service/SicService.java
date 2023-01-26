package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.OtpvClient;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.model.response.GenerateOtpResponse;

@Service
public class SicService {

	@Autowired
	OtpvClient otpv;
	
	public GenerateOtpResponse genrateOtp(GenerateOtpRequest request) {
		
		GenerateOtpResponse response = otpv.generaOtp(request);
		
		return response;
	}
	
	public GenerateOtpResponse genrateOtpMock(GenerateOtpRequest request) {
		
		GenerateOtpResponse response = otpv.generaOtpMock(request);
		
		return response;
	}
	
	//TODO continuare , manca checkpin 
	// flusso -> registra utente su iwdb , poi salvo anagrafica su ansc
	// chiusa registrazione
	// faccio login checkpin e setto a l1, controllo se mail e certificata se no certifico.
	// dopo certifica o se certificata mando otp mail e setto a l2.
	// controllo se ha gia acc dispo, se no registra dispo.
	// creo token e posso operare.
}

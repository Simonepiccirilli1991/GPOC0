package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.AnscClient;
import com.gwpoc.client.OtpvClient;
import com.gwpoc.fragment.model.CheckOtpRequest;
import com.gwpoc.fragment.model.CheckOtpResponse;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.response.GenerateOtpResponse;
import com.gwpoc.model.response.SicResponse;

@Service
public class SicService {

	@Autowired
	OtpvClient otpv;
	@Autowired
	AnscClient ansc;
	
//--------OTPV0 services---------------------------------------//
	public GenerateOtpResponse genrateOtp(GenerateOtpRequest request) {
		
		GenerateOtpResponse response = otpv.generaOtp(request);
		
		return response;
	}
	
	public GenerateOtpResponse genrateOtpMock(GenerateOtpRequest request) {
		
		GenerateOtpResponse response = otpv.generaOtpMock(request);
		
		return response;
	}
	
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {
		
		CheckOtpResponse response = otpv.checkOtp(request);
		
		return response;
	}
	
//-----------------ANSC0 services --------------------------------//
	public void checkPin(SicRequest request) {
		
		ansc.checkPin(request);;
		
	}
	
	public void certifyMail(SicRequest request) {
		
		ansc.certifyMail(request);
	}
	
	public void changePin(SicRequest request) {
		
		ansc.changePin(request);
	}
	
	//TODO continuare 
	// flusso -> registra utente su iwdb , poi salvo anagrafica su ansc
	// chiusa registrazione
	// faccio login checkpin e setto a l1, controllo se mail e certificata se no certifico.
	// dopo certifica o se certificata mando otp mail e setto a l2.
	// controllo se ha gia acc dispo, se no registra dispo.
	// creo token e posso operare.
}

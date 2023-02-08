package com.gwpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger logger = LoggerFactory.getLogger(SicService.class);
	
//--------OTPV0 services---------------------------------------//
	public GenerateOtpResponse genrateOtp(GenerateOtpRequest request) {
		logger.info("API :SicService - generateOtp -  START with raw request: {}", request);
		GenerateOtpResponse response = otpv.generaOtp(request);
		
		logger.info("API :SicService - generateOtp -  END response : {}", response);
		return response;
	}
	
	public GenerateOtpResponse genrateOtpMock(GenerateOtpRequest request) {
		logger.info("API :SicService - genrateOtpMock -  START with raw request: {}", request);
		GenerateOtpResponse response = otpv.generaOtpMock(request);
		
		logger.info("API :SicService - genrateOtpMock -  END response : {}", response);
		return response;
	}
	
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {
		logger.info("API :SicService - checkOtp -  START with raw request: {}", request);
		CheckOtpResponse response = otpv.checkOtp(request);
		
		logger.info("API :SicService - checkOtp -  END response : {}", response);
		return response;
	}
	
//-----------------ANSC0 services --------------------------------//
	public void checkPin(SicRequest request) {
		logger.info("API :SicService - checkPin -  START with raw request: {}", request);
		
		ansc.checkPin(request);;
		
		logger.info("API :SicService - checkPin -  END - complete");
	}
	
	public void certifyMail(SicRequest request) {
		logger.info("API :SicService - SicRequest -  START with raw request: {}", request);
		
		ansc.certifyMail(request);
		
		logger.info("API :SicService - certifyMail -  END - complete");
	}
	
	public void changePin(SicRequest request) {
		logger.info("API :SicService - checkPin -  START with raw request: {}", request);
		
		ansc.changePin(request);
		
		logger.info("API :SicService - changePin -  END - complete");
	}
	
	//TODO continuare 
	// flusso -> registra utente su iwdb , poi salvo anagrafica su ansc
	// chiusa registrazione
	// faccio login checkpin e setto a l1, controllo se mail e certificata se no certifico.
	// dopo certifica o se certificata mando otp mail e setto a l2.
	// controllo se ha gia acc dispo, se no registra dispo.
	// creo token e posso operare.
}

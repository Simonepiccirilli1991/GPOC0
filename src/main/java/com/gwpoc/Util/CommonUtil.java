package com.gwpoc.Util;

import org.springframework.stereotype.Component;

import com.gwpoc.fragment.model.CheckOtpRequest;
import com.gwpoc.fragment.model.CheckOtpResponse;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.request.SessionRequest;

@Component
public class CommonUtil {

	public SessionRequest createSessionRequestL1(String bt) {

		SessionRequest iRequest = new SessionRequest();
		iRequest.setBt(bt);
		iRequest.setChannel("Web");
		iRequest.setScope("l1");

		return iRequest;
	}
	
	public SessionRequest updateSessionRequestL2(String bt) {

		SessionRequest iRequest = new SessionRequest();
		iRequest.setBt(bt);
		iRequest.setChannel("Web");
		iRequest.setScope("l2");

		return iRequest;
	}

	public GenerateOtpRequest createOtpRequest(String bt, String email) {

		GenerateOtpRequest response = new GenerateOtpRequest();
		response.setAbi("abi");
		response.setBt(bt);
		response.setProf("Web");
		response.setEmail(email);

		return response;
	}

	public SicRequest changeRequest(PinRequest request) {

		SicRequest response = new SicRequest();
		response.setBt(request.getBt());
		response.setOtp(request.getOtp());
		response.setPin(request.getPin());
		response.setTrxId(request.getTrxId());

		return response;
	}
	
	public GenerateOtpRequest generateOtpRequest(String bt, String email) {
		
		GenerateOtpRequest response = new GenerateOtpRequest();
		response.setAbi("Abi");
		response.setBt(bt);
		response.setEmail(email);
		response.setProf("Web");
		response.setTipoEvento(0);
		
		return response;
	}
	
	public CheckOtpRequest generateCheckOtpRequest(String bt, String otp, String trxId) {
		
		CheckOtpRequest response = new CheckOtpRequest();
		response.setBt(bt);
		response.setOtp(otp);
		response.setTransactionId(trxId);
		response.setProfile("Web");
		
		return response;
	}
}

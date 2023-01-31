package com.gwpoc.Util;

import org.springframework.stereotype.Component;

import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.request.SessionRequest;

@Component
public class CommonUtil {

	public SessionRequest createSessionRequestL1(SicRequest request) {

		SessionRequest iRequest = new SessionRequest();
		iRequest.setBt(request.getBt());
		iRequest.setChannel("Web");
		iRequest.setScope("l1");

		return iRequest;
	}

	public GenerateOtpRequest createOtpRequest(SicRequest request, String email) {

		GenerateOtpRequest response = new GenerateOtpRequest();
		response.setAbi("abi");
		response.setBt(request.getBt());
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
}

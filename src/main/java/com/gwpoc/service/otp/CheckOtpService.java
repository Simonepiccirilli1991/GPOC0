package com.gwpoc.service.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.Util.CommonUtil;
import com.gwpoc.model.action.BaseActionService;
import com.gwpoc.model.request.OtpRequest;
import com.gwpoc.model.response.OtpResponse;
import com.gwpoc.service.SessionService;
import com.gwpoc.service.SicService;

@Service
public class CheckOtpService extends BaseActionService<OtpRequest, OtpResponse>{
	
	@Autowired
	SicService sic;
	@Autowired
	SessionService sessionServ;
	@Autowired
	CommonUtil utils;
	
	@Override
	public OtpResponse lunchService_(OtpRequest iRequest, HttpHeaders httpHeaders) {
		
		OtpResponse response = new OtpResponse();
		
		// checkOtp
		sic.checkOtp(utils.generateCheckOtpRequest(iRequest.getBt(), iRequest.getOtp(), iRequest.getTrxId()));
		
		
		//update session per settare in l2 la sicurezza
		sessionServ.update(utils.updateSessionRequestL2(iRequest.getBt()));
		
		response.setAction(ActionEnum.CONSENT);
		
		return response;
	}
	

}

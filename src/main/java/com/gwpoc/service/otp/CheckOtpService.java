package com.gwpoc.service.otp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gwpoc.service.StatusService;

@Service
public class CheckOtpService extends BaseActionService<OtpRequest, OtpResponse>{
	
	@Autowired
	SicService sic;
	@Autowired
	SessionService sessionServ;
	@Autowired
	CommonUtil utils;
	@Autowired
	StatusService statusServ;
	
	Logger logger = LoggerFactory.getLogger(CheckOtpService.class);
	
	@Override
	public OtpResponse lunchService_(OtpRequest iRequest, HttpHeaders httpHeaders) {
		
		logger.info("API :CheckOtpService - START with raw request: {}", iRequest);
		
		OtpResponse response = new OtpResponse();
		
		// checkOtp
		sic.checkOtp(utils.generateCheckOtpRequest(iRequest.getBt(), iRequest.getOtp(), iRequest.getTrxId()));
		
		
		//update session per settare in l2 la sicurezza
		sessionServ.update(utils.updateSessionRequestL2(iRequest.getBt()));
		
		// chiamo la status per vedere se deve creare acc-dispo o lo ha gia setta lei direttamente action
		response.setAction(statusServ.getStatus(iRequest.getBt()).getAction());
		logger.info("API :CheckOtpService - END with response: {}", response);
		
		return response;
	}
	

}

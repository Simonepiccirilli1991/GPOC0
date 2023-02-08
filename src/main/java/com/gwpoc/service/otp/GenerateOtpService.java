package com.gwpoc.service.otp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.Util.CommonUtil;
import com.gwpoc.client.AnscClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.model.action.BaseActionService;
import com.gwpoc.model.request.OtpRequest;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.OtpResponse;
import com.gwpoc.service.AnagraficaService;
import com.gwpoc.service.SessionService;
import com.gwpoc.service.SicService;

@Service
public class GenerateOtpService extends BaseActionService<OtpRequest, OtpResponse>{

	@Autowired
	SessionService sessionServ;
	@Autowired
	CommonUtil utils;
	@Autowired
	AnagraficaService anag;
	@Autowired
	SicService sic;
	
	Logger logger = LoggerFactory.getLogger(GenerateOtpService.class);
	
	@Override
	public OtpResponse lunchService_(OtpRequest iRequest, HttpHeaders httpHeaders) {
		
		logger.info("API :GenerateOtpService - START with raw request: {}", iRequest);
		
		OtpResponse response = new OtpResponse();
		
		SessionRequest sessReq = new SessionRequest();
		sessReq.setChannel("Web");
		sessReq.setBt(iRequest.getBt());
		// sia che sono in resend o che sia il primo devo comunque avere la sessione in l1
		String sessionScope = sessionServ.getSession(sessReq).getScope();
		
		if(ObjectUtils.isEmpty(sessionScope) || !sessionScope.equals("l1")) {
			logger.error("Client :GenerateOtpService - EXCEPTION cause by session : {}", sessionScope);
			throw new AppException("TODO");
		}
		//controllo che mail inserita sia stessa che e salvata su anagrafica se non lo e prendo quella di anagrafica a cui mandare otp
		AnagraficaResponse anagraficaResponse = anag.getAnagrafica(iRequest.getBt());
		
		String email = iRequest.getEmail();
		if(email != anagraficaResponse.getMail())
			email = anagraficaResponse.getMail();
		
		GenerateOtpRequest otpReq = new GenerateOtpRequest();
		otpReq.setAbi("abi");
		var trxId = sic.genrateOtpMock(utils.generateOtpRequest(iRequest.getBt(), email)).getTrxId();
		
		response.setTrxId(trxId);
		response.setAction(ActionEnum.CHECKOTP);
		logger.info("API :GenerateOtpService - END with response: {}", response);
		
		return response;
	}

	
	
}

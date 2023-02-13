package com.gwpoc.service.pin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.Util.CommonUtil;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.action.BaseActionService;
import com.gwpoc.model.request.AuthRequest;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.SessionService;
import com.gwpoc.service.SicService;
import com.gwpoc.service.StatusService;
import com.gwpoc.service.otp.CheckOtpService;
import com.gwpoc.service.validation.ValidAuthService;

@Service
public class CertifyMailService extends BaseActionService<PinRequest, PinResponse>{

	@Autowired
	SicService sicurezza;
	@Autowired
	SessionService session;
	@Autowired
	CommonUtil utils;
	@Autowired
	StatusService statusServ;
	@Autowired
	ValidAuthService authService;
	
	Logger logger = LoggerFactory.getLogger(CertifyMailService.class);
	
	@Override
	public PinResponse lunchService_(PinRequest iRequest, HttpHeaders httpHeaders) {
		
		logger.info("API :CertifyMailService - START with raw request: {}", iRequest);
		
		PinResponse response = new PinResponse();
		//fatto sta porcata perchè non mi va di refactorare i metodi
		//TODO quando hai voglia fallo bene cane!
		SicRequest iReq = utils.changeRequest(iRequest);
		//controllo che sessione sia creata
		SessionResponse sessResponse = session.getSession(utils.createSessionRequestL1(iReq.getBt()));
		
		if(ObjectUtils.isEmpty(sessResponse) || ObjectUtils.isEmpty(sessResponse.getScope())) {
			logger.error("Client :CertifyMailService - EXCEPTION cause by session : {}", sessResponse);
			throw new AppException("No session valid found");
		}	
		
		// valido token con dati solo in questo caso e setto dati
		AuthRequest request = new AuthRequest();
		request.setBt(iRequest.getBt());
		request.setPin(iRequest.getPin());
		httpHeaders.add("CheckDouble", "true");
		
		authService.validateAuthWithData(request, httpHeaders);
		
		sicurezza.certifyMail(iReq);
		
		//updato sessione di sicurezza
		session.update(utils.updateSessionRequestL2(iRequest.getBt()));
		response.setAction(statusServ.getStatus(iRequest.getBt()).getAction());
		
		logger.info("API :CertifyMailService - END with response: {}", response);
		return response;
	}

}

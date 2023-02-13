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
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.service.AnagraficaService;
import com.gwpoc.service.SessionService;
import com.gwpoc.service.SicService;
import com.gwpoc.service.validation.GetAuthService;

@Service
public class CheckPinService extends BaseActionService<PinRequest, PinResponse>{

	@Autowired
	SicService sicurezza;
	@Autowired
	AnagraficaService anag;
	@Autowired
	SessionService session;
	@Autowired
	GetAuthService authService;
	@Autowired
	CommonUtil utils;
	
	Logger logger = LoggerFactory.getLogger(CheckPinService.class);
	
	
	@Override
	public PinResponse lunchService_(PinRequest iRequest, HttpHeaders httpHeaders) {
		
		logger.info("API :CheckPinService - START with raw request: {}", iRequest);
		
		PinResponse response = new PinResponse();
		//fatto sta porcata perchè non mi va di refactorare i metodi
		//TODO quando hai voglia fallo bene cane!
		SicRequest iReq = utils.changeRequest(iRequest);
		sicurezza.checkPin(iReq);
		
		AnagraficaResponse anagrafica = anag.getAnagrafica(iRequest.getBt());
		
		if(ObjectUtils.isEmpty(anagrafica) || ObjectUtils.isEmpty(anagrafica.getMailCertificata())) {
			logger.error("Client :CheckPinService - EXCEPTION cause by anagrafica : {}", anagrafica);
			throw new AppException("TODO");
		}
		// se mail certificata mando action sendotp per sicurezza l2
		if(anagrafica.getMailCertificata()) {
			response.setAction(ActionEnum.SENDOTP);
			session.createSession(utils.createSessionRequestL1(iRequest.getBt()));
			
		}
		// se non certificata deve certificare e mando otp alla mail salvata su anagrafica 
		else {
			response.setAction(ActionEnum.CERTIFYMAIL);
			response.setEmail(anagrafica.getMail());
			String trxId = sicurezza.genrateOtpMock(utils.createOtpRequest(iRequest.getBt(), anagrafica.getMail())).getTrxId();
			response.setTrxId(trxId);
		}
		
		// creo auth e torno
		AuthRequest request = new AuthRequest();
		request.setBt(iRequest.getBt());
		request.setPin(iRequest.getPin());
		
		String auth = authService.generateAuth(request, httpHeaders).getHeaders().getFirst("Authorization");
		if(ObjectUtils.isEmpty(auth))
			throw new AppException("TBD");
		
		httpHeaders.add("Authorization", auth);
		response.setHttpHeaders(httpHeaders);
		logger.info("API :CheckPinService - END with response: {}", response);
		return response;
	}

	
}

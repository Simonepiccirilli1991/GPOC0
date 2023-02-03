package com.gwpoc.service.pin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.Util.CommonUtil;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.action.BaseActionService;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.service.AnagraficaService;
import com.gwpoc.service.SessionService;
import com.gwpoc.service.SicService;

@Service
public class CheckPinService extends BaseActionService<PinRequest, PinResponse>{

	@Autowired
	SicService sicurezza;
	@Autowired
	AnagraficaService anag;
	@Autowired
	SessionService session;
	@Autowired
	CommonUtil utils;
	@Override
	public PinResponse lunchService_(PinRequest iRequest, HttpHeaders httpHeaders) {
		
		PinResponse response = new PinResponse();
		//fatto sta porcata perchè non mi va di refactorare i metodi
		//TODO quando hai voglia fallo bene cane!
		SicRequest iReq = utils.changeRequest(iRequest);
		sicurezza.checkPin(iReq);
		
		AnagraficaResponse anagrafica = anag.getAnagrafica(iRequest.getBt());
		
		if(ObjectUtils.isEmpty(anagrafica) || ObjectUtils.isEmpty(anagrafica.getMailCertificata()))
			throw new AppException("TODO");
		// se mail certificata mando action sendotp per sicurezza l2
		if(anagrafica.getMailCertificata()) {
			response.setAction(ActionEnum.SENDOTP);
			session.createSession(utils.createSessionRequestL1(iRequest.getBt()));
			return response;
			
		}
		// se non certificata deve certificare e mando otp alla mail salvata su anagrafica 
		else {
			response.setAction(ActionEnum.CERTIFYMAIL);
			response.setEmail(anagrafica.getMail());
			String trxId = sicurezza.genrateOtpMock(utils.createOtpRequest(iRequest.getBt(), anagrafica.getMail())).getTrxId();
			response.setTrxId(trxId);
			return response;
		}
	}

	
}

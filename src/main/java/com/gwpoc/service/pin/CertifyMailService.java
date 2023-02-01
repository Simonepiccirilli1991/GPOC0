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
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.SessionService;
import com.gwpoc.service.SicService;

@Service
public class CertifyMailService extends BaseActionService<PinRequest, PinResponse>{

	@Autowired
	SicService sicurezza;
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
		//controllo che sessione sia creata non mi interessa a che livello
		SessionResponse sessResponse = session.getSession(utils.createSessionRequestL1(iReq.getBt()));
		
		if(ObjectUtils.isEmpty(sessResponse) || ObjectUtils.isEmpty(sessResponse.getScope()))
			throw new AppException("No session valid found");
			
		sicurezza.certifyMail(iReq);
		
		response.setAction(ActionEnum.CONSENT);
		return response;
	}

}

package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.model.response.SessionResponse;

@Service
public class PinServices {

	@Autowired
	SicService sicurezza;
	@Autowired
	AnagraficaService anag;
	@Autowired
	SessionService session;
	
	//checkMail
	public PinResponse checkPin(SicRequest request) {
		
		PinResponse response = new PinResponse();
		
		sicurezza.checkPin(request);
		
		AnagraficaResponse anagrafica = anag.getAnagrafica(request.getBt());
		
		if(ObjectUtils.isEmpty(anagrafica) || ObjectUtils.isEmpty(anagrafica.getMailCertificata()))
			throw new AppException("TODO");
		// se mail certificata apposto implementare action consetnt
		if(anagrafica.getMailCertificata()) {
			response.setAction(null);
			session.createSession(createSessionRequest(request));
			return response;
			
		}
		// se non certificata deve certificare e mando otp alla mail salvata su anagrafica 
		else {
			response.setAction(null);
			response.setEmail(anagrafica.getMail());
			String trxId = sicurezza.genrateOtpMock(createOtpRequest(request, anagrafica.getMail())).getTrxId();
			response.setTrxId(trxId);
			return response;
		}
	}
	
	public String certifyMail(SicRequest request) {
		
		//controllo che sessione sia creata non mi interessa a che livello
		SessionResponse sessResponse = session.getSession(createSessionRequest(request));
		
		if(ObjectUtils.isEmpty(sessResponse) || ObjectUtils.isEmpty(sessResponse.getScope()))
			throw new AppException("No session valid found");
			
		sicurezza.certifyMail(request);
		
		return "Mail correctly certified";
	}
	
	private SessionRequest createSessionRequest(SicRequest request) {
		
		SessionRequest iRequest = new SessionRequest();
		iRequest.setBt(request.getBt());
		iRequest.setChannel("Web");
		iRequest.setScope("l1");
		
		return iRequest;
	}
	
	private GenerateOtpRequest createOtpRequest(SicRequest request, String email) {
		
		GenerateOtpRequest response = new GenerateOtpRequest();
		response.setAbi("abi");
		response.setBt(request.getBt());
		response.setProf("Web");
		response.setEmail(email);
		
		return response;
	}
}

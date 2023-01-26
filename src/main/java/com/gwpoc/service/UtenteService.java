package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.cach.SessionChService;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.fragment.model.Utente;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.UtenteResponse;

@Service
public class UtenteService {

	@Autowired
	IwdbClient iwdbClient;
	@Autowired
	SessionChService sessionChService;
	@Autowired
	AnagraficaService anagService;
	
	// registra utente
	public UtenteResponse registra(UtenteRequest request) {
		
		UtenteResponse response = new UtenteResponse();
		
		UtenteIwResponse iResp = iwdbClient.registraUt(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError())
			throw new AppException("ERKO-02");
		
		anagService.insertAnagrafica(request, iResp.getBt());
		
		
		response.setBt(iResp.getBt());
		response.setRegisteredUpdated(true);
		
		return response;
	}
	
	//update utente
	public UtenteResponse update(UtenteRequest request) {
		
		UtenteResponse response = new UtenteResponse();
		
		// controllo che utente sia in sessione di sicurezza l2
		SessionRequest sessRequest = new SessionRequest();
		sessRequest.setBt(request.getBt());
		
		if(!sessionChService.checkL2(sessRequest)) {
			throw new AppException("ERKO-10");
		}
		
		UtenteIwResponse iResp = iwdbClient.updateUtente(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError())
			throw new AppException("ERKO-02");
		
		response.setMsg("Dati aggiornati con successo");
		response.setRegisteredUpdated(true);
		
		return response;
		
	}
	
	// get utente
	public UtenteResponse getUtente(String bt) {
		
		
		UtenteResponse response = new UtenteResponse();
		
		UtenteIwResponse iResp = iwdbClient.getUtente(bt);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError())
			throw new AppException("ERKO-02");
		
		response.setUtente(iResp.getUtente());
		
		return response;
	}
	
}

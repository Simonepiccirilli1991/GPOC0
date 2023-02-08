package com.gwpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger logger = LoggerFactory.getLogger(UtenteService.class);
	
	// registra utente
	public UtenteResponse registra(UtenteRequest request) {
		
		logger.info("API :UtenteService - registra -  START with raw request: {}", request);
		
		UtenteResponse response = new UtenteResponse();
		
		UtenteIwResponse iResp = iwdbClient.registraUt(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :UtenteService - registra - EXCEPTION cause by iwdbClient");
			throw new AppException("ERKO-02");
		}
		anagService.insertAnagrafica(request, iResp.getBt());
		
		
		response.setBt(iResp.getBt());
		response.setRegisteredUpdated(true);
		logger.info("API :UtenteService - registra - END with response: {}", response);
		
		return response;
	}
	
	//update utente
	public UtenteResponse update(UtenteRequest request) {
		logger.info("API :UtenteService - update -  START with raw request: {}", request);
		
		UtenteResponse response = new UtenteResponse();
		
		// controllo che utente sia in sessione di sicurezza l2
		SessionRequest sessRequest = new SessionRequest();
		sessRequest.setBt(request.getBt());
		
		if(!sessionChService.checkL2(sessRequest)) {
			logger.error("Client :UtenteService - update - EXCEPTION cause by session");
			throw new AppException("ERKO-10");
		}
		
		UtenteIwResponse iResp = iwdbClient.updateUtente(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :UtenteService - update - EXCEPTION cause by iwdb on update");
			throw new AppException("ERKO-02");
		}
		response.setMsg("Dati aggiornati con successo");
		response.setRegisteredUpdated(true);
		
		logger.info("API :UtenteService - update - END with response: {}", response);
		return response;
		
	}
	
	// get utente
	public UtenteResponse getUtente(String bt) {
		logger.info("API :UtenteService - get -  START with raw request: {}", bt);
		
		UtenteResponse response = new UtenteResponse();
		
		UtenteIwResponse iResp = iwdbClient.getUtente(bt);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :UtenteService - get - EXCEPTION cause by iwdb on get utente");
			throw new AppException("ERKO-02");
		}
		response.setUtente(iResp.getUtente());
		
		logger.info("API :UtenteService - get - END with response: {}", response);
		return response;
	}
	
}

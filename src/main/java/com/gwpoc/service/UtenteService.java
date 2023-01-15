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
	
	// registra utente
	public UtenteResponse registra(UtenteRequest request) {
		
		UtenteResponse response = new UtenteResponse();
		
		UtenteIwResponse iResp = iwdbClient.registraUt(request);
		
		response.setBt(iResp.getBt());
		response.setRegisteredUpdated(true);
		
		return response;
	}
	
	//update utente
	public UtenteResponse update(UtenteRequest request) {
		
		UtenteResponse response = new UtenteResponse();
		
		// controllo che utente sia in sessione di sicurezza l2
		SessionRequest sessRequest = new SessionRequest();
		
		if(!sessionChService.checkL2(sessRequest)) {
			throw new AppException("ERKO-10");
		}
		
		iwdbClient.updateUtente(request);
		
		response.setMsg("Dati aggiornati con successo");
		response.setRegisteredUpdated(true);
		
		return response;
		
	}
	
	// get utente
	public UtenteResponse getUtente(String bt) {
		
		
		UtenteResponse response = new UtenteResponse();
		
		UtenteIwResponse iResp = iwdbClient.getUtente(bt);
		
		response.setUtente(mapRespToDto(iResp));
		
		return response;
	}
	
	
	private Utente mapRespToDto(UtenteIwResponse dto) {
		
		Utente response = new Utente();
		response.setBt(dto.getBt());
		response.setCf(dto.getCf());
		response.setChannel(dto.getChannel());
		response.setId(dto.getId());
		response.setUsername(dto.getUsername());
		
		if(!ObjectUtils.isEmpty(dto.getAccount()))
			response.setAccount(dto.getAccount());
		
		return response;
	}
}

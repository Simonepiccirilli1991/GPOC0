package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.IwdbClient;
import com.gwpoc.fragment.cach.SessionChService;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
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
		response.setRegistered(true);
		
		return response;
	}
	
	//update utente
	public UtenteResponse update(UtenteRequest request) {
		
		UtenteResponse response = new UtenteResponse();
		
		// controllo che utente sia in sessione di sicurezza l2
		SessionRequest sessRequest = new SessionRequest();
		
		if(!sessionChService.checkL2(sessRequest)) {
			//TODO implementare lancio eccezzione utente non in l2
		}
		
		iwdbClient.updateUtente(request);
		
		response.setMsg("Dati aggiornati con successo");
		
		return response;
		
	}
}

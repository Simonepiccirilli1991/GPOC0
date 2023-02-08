package com.gwpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwpoc.client.AnscClient;
import com.gwpoc.fragment.model.AnagraficaRequest;
import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.AnagraficaResponse;

@Service
public class AnagraficaService {

	@Autowired
	AnscClient ansc;
	
	Logger logger = LoggerFactory.getLogger(AnagraficaService.class);
	
	// usato in registrazione utente
	public void insertAnagrafica(UtenteRequest request, String bt) {
		
		logger.info("API :AnagraficaService - insert -  START with raw request: {} , and bt {}", request, bt);
		
		AnagraficaRequest iRequest = new AnagraficaRequest();
		iRequest.setBancaId(request.getBancaId());
		iRequest.setBt(bt);
		iRequest.setCelluare(request.getCellulare());
		iRequest.setCf(request.getCf());
		iRequest.setCognome(request.getCognome());
		iRequest.setMail(request.getMail());
		iRequest.setNome(request.getNome());
		//setto pin hardocato per ora
		iRequest.setPin("1111");
		
		ansc.insertAnagrafica(iRequest);
		logger.info("API :AnagraficaService - insert - END completed");
	}
	
	
	public AnagraficaResponse getAnagrafica(String bt) {
		logger.info("API :AnagraficaService - get -  START with raw request: {}", bt);
		
		AnagraficaResponse response = ansc.getAnagrafica(bt);
		
		logger.info("API :AnagraficaService - get - END with response : {}", response);
		return response;
	}
}

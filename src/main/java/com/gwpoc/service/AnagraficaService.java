package com.gwpoc.service;

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
	// usato in registrazione utente
	public void insertAnagrafica(UtenteRequest request, String bt) {
		
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
	}
	
	
	public AnagraficaResponse getAnagrafica(String bt) {
		
		AnagraficaResponse response = ansc.getAnagrafica(bt);
		
		return response;
	}
}

package com.gwpoc.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.model.request.UtenteRequest;

import reactor.core.publisher.Mono;

@Component
public class IwdbClient {

	private String iwdbUri = "http://localhost:8083";
	WebClient webClient = WebClient.create(iwdbUri);
	
	// registra utente
	public UtenteIwResponse registraUt(UtenteRequest request) {
		
		UtenteIwResponse response = null;
		Mono<UtenteIwResponse> iResp = null;
		
		try {
			iResp = webClient.post()
					.uri("prodotti/transact")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), UtenteRequest.class)
					.retrieve()
					.bodyToMono(UtenteIwResponse.class);
		}
		
		catch(Exception e) {
			//TODO implementare gestione errore
		}
		
		//TODO inserire controlli con eccezione 
		if(ObjectUtils.isEmpty(iResp)) {
			
		}
		
		response = iResp.block();
		
		return response;
	}
	
	public UtenteIwResponse updateUtente(UtenteRequest request) {

		UtenteIwResponse response = null;
		Mono<UtenteIwResponse> iResp = null;

		try {
			iResp = webClient.post()
					.uri("ut/update")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), UtenteRequest.class)
					.retrieve()
					.bodyToMono(UtenteIwResponse.class);
		}

		catch(Exception e) {
			//TODO implementare gestione errore
		}

		//TODO inserire controlli con eccezione 
		if(ObjectUtils.isEmpty(iResp)) {

		}

		response = iResp.block();

		return response;
	}
}

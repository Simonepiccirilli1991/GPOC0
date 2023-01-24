package com.gwpoc.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.fragment.iwdb.OrdiniIwResponse;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.request.OrdiniRequest;
import com.gwpoc.model.request.UtenteRequest;

import reactor.core.publisher.Mono;

@Component
public class IwdbClient {

	@Value("${config.iwdb0.end-point}")
	private String iwdbUri;
	
	WebClient webClient = WebClient.create(iwdbUri);

	// registra utente
	public UtenteIwResponse registraUt(UtenteRequest request) {

		UtenteIwResponse response = null;
		Mono<UtenteIwResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/ut/inserts").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), UtenteRequest.class)
					.retrieve()
					.bodyToMono(UtenteIwResponse.class);
		}
		catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();

		return response;
	}
	// update utente
	public UtenteIwResponse updateUtente(UtenteRequest request) {

		UtenteIwResponse response = null;
		Mono<UtenteIwResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/ut/update").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), UtenteRequest.class)
					.retrieve()
					.bodyToMono(UtenteIwResponse.class);
		}
		catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();


		return response;
	}
	//get utente
	public UtenteIwResponse getUtente(String bt) {

		UtenteIwResponse response = null;
		Mono<UtenteIwResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/ut/get/"+bt).toUriString();
		try {
			iResp = webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(UtenteIwResponse.class);
		}
		catch(Exception e) {
			throw new AppException("");
		}

		response = iResp.block();

		return response;
	}
	
	// account calls
	// insert account
	public AccountIwResponse insertAccount(AccountRequest request) {
		
		AccountIwResponse response = null;
		Mono<AccountIwResponse> iResp = null;	
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/acc/insert").toUriString();
		try {	
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), AccountRequest.class)
					.retrieve()
					.bodyToMono(AccountIwResponse.class);
			
		}catch(Exception e) {
			throw new AppException("");
		}		
		response = iResp.block();
		
		return response;
	}
	//update account
	public AccountIwResponse updateAcc(AccountRequest request) {

		AccountIwResponse response = null;
		Mono<AccountIwResponse> iResp = null;	
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/acc/update").toUriString();
		try {	
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), AccountRequest.class)
					.retrieve()
					.bodyToMono(AccountIwResponse.class);

		}catch(Exception e) {
			throw new AppException("");
		}		
		response = iResp.block();

		return response;
	}
	//get account
	public AccountIwResponse getAcc(String bt) {

		AccountIwResponse response = null;
		Mono<AccountIwResponse> iResp = null;	
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/acc/get"+bt).toUriString();
		try {	
			iResp = webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(AccountIwResponse.class);

		}catch(Exception e) {
			throw new AppException("");
		}		
		response = iResp.block();

		return response;
	}
	
	// orderCall
	//createOrder
	public OrdiniIwResponse creaOrdine(OrdiniRequest request) {
		
		OrdiniIwResponse response = null;
		Mono<OrdiniIwResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/ordini/insert").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), OrdiniRequest.class)
					.retrieve()
					.bodyToMono(OrdiniIwResponse.class);
		}catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();
		
		return response;
	}
	// get order
	public OrdiniIwResponse getOrder(OrdiniRequest request) {
		
		OrdiniIwResponse response = null;
		Mono<OrdiniIwResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/ordini/get").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), OrdiniRequest.class)
					.retrieve()
					.bodyToMono(OrdiniIwResponse.class);
		}catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();
		
		return response;
	}
	//
}

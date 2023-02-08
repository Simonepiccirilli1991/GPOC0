package com.gwpoc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.fragment.iwdb.OrdiniIwResponse;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
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
	Logger logger = LoggerFactory.getLogger(IwdbClient.class);
	
	// registra utente
	public UtenteIwResponse registraUt(UtenteRequest request) {
		logger.info("CLIENT :IwdbClient - registraUt -  START with raw request: {}", request);
		
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
			logger.error("Client : IwdbClient - registraUt - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - registraUt -  END response: {}", response);
		return response;
	}
	// update utente
	public UtenteIwResponse updateUtente(UtenteRequest request) {
		logger.info("CLIENT :IwdbClient - updateUtente -  START with raw request: {}", request);
		
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
			logger.error("Client : IwdbClient - updateUtente - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();

		logger.info("CLIENT :IwdbClient - updateUtente -  END response: {}", response);
		return response;
	}
	//get utente
	public UtenteIwResponse getUtente(String bt) {
		logger.info("CLIENT :IwdbClient - getUtente -  START with raw request: {}", bt);
		
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
			logger.error("Client : IwdbClient - getUtente - EXCEPTION", e);
			throw new AppException("");
		}

		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - getUtente -  END response: {}", response);
		return response;
	}
	
	// account calls
	// insert account
	public AccountIwResponse insertAccount(AccountRequest request) {
		logger.info("CLIENT :IwdbClient - insertAccount -  START with raw request: {}", request);
		
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
			logger.error("Client : IwdbClient - insertAccount - EXCEPTION", e);
			throw new AppException("");
		}		
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - insertAccount -  END response: {}", response);
		return response;
	}
	//update account
	public AccountIwResponse updateAcc(AccountRequest request) {
		logger.info("CLIENT :IwdbClient - updateAcc -  START with raw request: {}", request);
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
			logger.error("Client : IwdbClient - updateAcc - EXCEPTION", e);
			throw new AppException("");
		}		
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - updateAcc -  END response: {}", response);
		return response;
	}
	//get account
	public AccountIwResponse getAcc(String bt) {
		logger.info("CLIENT :IwdbClient - getAcc -  START with raw request: {}", bt);
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
			logger.error("Client : IwdbClient - getAcc - EXCEPTION", e);
			throw new AppException("");
		}		
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - getAcc -  END response: {}", response);
		return response;
	}
	
	// orderCall
	//createOrder
	public OrdiniIwResponse creaOrdine(OrdiniRequest request) {
		logger.info("CLIENT :IwdbClient - creaOrdine -  START with raw request: {}", request);
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
			logger.error("Client : IwdbClient - creaOrdine - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - creaOrdine -  END response: {}", response);
		return response;
	}
	// get order
	public OrdiniIwResponse getOrder(OrdiniRequest request) {
		logger.info("CLIENT :IwdbClient - getOrder -  START with raw request: {}", request);
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
			logger.error("Client : IwdbClient - getOrder - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - getOrder -  END response: {}", response);
		return response;
	}
	// get status
	public StatusIwResponse getstatus(String bt) {
		logger.info("CLIENT :IwdbClient - getstatus -  START with raw request: {}", bt);
		StatusIwResponse response = null;
		Mono<StatusIwResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/ut/status/"+bt).toUriString();
		try {
			iResp = webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(StatusIwResponse.class);
		}catch(Exception e) {
			logger.error("Client : IwdbClient - getstatus - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		logger.info("CLIENT :IwdbClient - getstatus -  END response: {}", response);
		return response;
	}
	
}

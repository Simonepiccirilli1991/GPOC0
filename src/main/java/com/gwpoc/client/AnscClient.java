package com.gwpoc.client;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.AnagraficaRequest;
import com.gwpoc.fragment.model.SicRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.SicResponse;
import com.gwpoc.service.AccountService;

import reactor.core.publisher.Mono;

@Component
public class AnscClient {

	@Value("${config.ansc0.end-point}")
	private String anscUri;
	
	Logger logger = LoggerFactory.getLogger(AnscClient.class);
	
	WebClient webClient = WebClient.create(anscUri);

	// insert anagrafica
	// se esplode fa retry 2 volte su timeout e clientError
	@Retryable(retryFor ={TimeoutException.class, HttpClientErrorException.class}, maxAttempts = 3)
	public void insertAnagrafica(AnagraficaRequest request) {
		
		logger.info("CLIENT :AnscClient - insertAnag -  START with raw request: {}", request);
		
		AnagraficaResponse response = null;
		Mono<AnagraficaResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(anscUri + "/ag/insert").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), AnagraficaRequest.class)
					.retrieve()
					.bodyToMono(AnagraficaResponse.class);
		}
		catch(HttpClientErrorException e) {
			throw e;
		}
		catch(Exception e) {
			logger.error("Client : AnscClient - insertAnagrafica - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException(response.getErrMsg());
		
		logger.info("CLIENT :AnscClient - insertAnag -  END response: {}", response);
		
	}
	// get anagrafica
	public AnagraficaResponse getAnagrafica(String bt) {
		logger.info("CLIENT :AnscClient - getAnagrafica -  START with raw request: {}", bt);
		
		AnagraficaResponse response = null;
		Mono<AnagraficaResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(anscUri + "/ag/get/"+bt).toUriString();
		try {
			iResp = webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(AnagraficaResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : AnscClient - getAnagrafica - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException(response.getErrMsg());
		
		logger.info("CLIENT :AnscClient - getAnagrafica -  END response: {}", response);
		return response;
	}
	
	//certifyMail
	public void certifyMail(SicRequest request) {
		logger.info("CLIENT :AnscClient - certifyMail -  START with raw request: {}", request);
		
		SicResponse response = null;
		Mono<SicResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(anscUri + "/sic/certify").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SicRequest.class)
					.retrieve()
					.bodyToMono(SicResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : AnscClient - certifyMail - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException(response.getErrMsg());
		
		logger.info("CLIENT :AnscClient - certifyMail -  END response: {}", response);
		
	}
	//checkPin
	public void checkPin(SicRequest request) {
		logger.info("CLIENT :AnscClient - checkPin -  START with raw request: {}", request);
		
		SicResponse response = null;
		Mono<SicResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(anscUri + "/sic/checkpin").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SicRequest.class)
					.retrieve()
					.bodyToMono(SicResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : AnscClient - checkPin - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException(response.getErrMsg());
		
		logger.info("CLIENT :AnscClient - checkPin -  END response: {}", response);
		
	}
	//changePin
	public void changePin(SicRequest request) {
		logger.info("CLIENT :AnscClient - changePin -  START with raw request: {}", request);
		
		SicResponse response = null;
		Mono<SicResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(anscUri + "/sic/changepin").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SicRequest.class)
					.retrieve()
					.bodyToMono(SicResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : AnscClient - changePin - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException("TODO");
		
		logger.info("CLIENT :AnscClient - changePin -  END response: {}", response);
	}
}

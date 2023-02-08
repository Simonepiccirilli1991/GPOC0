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
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;

import reactor.core.publisher.Mono;

@Component
public class CachClient {

	@Value("${config.cach0.end-point}")
	private String cach0Uri;
	WebClient webClient = WebClient.create(cach0Uri);

	Logger logger = LoggerFactory.getLogger(CachClient.class);
	
	public SessionResponse getSession(SessionRequest request) {
		logger.info("CLIENT :CachClient - getSession -  START with raw request: {}", request);
		
		SessionResponse response = null;
		Mono<SessionResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(cach0Uri + "/sess/get").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SessionRequest.class)
					.retrieve()
					.bodyToMono(SessionResponse.class);
		}

		catch(Exception e) {
			logger.error("Client : CachClient - getSession - EXCEPTION ", e.getCause());
			throw new AppException("SSKO-03");

		}
		if(ObjectUtils.isEmpty(iResp)) {

			throw new AppException("SSKO-03");
		}

		response = iResp.block();
		logger.info("CLIENT :CachClient - getSession -  END response: {}", response);
		
		return response;
	}
	
	public SessionResponse createSession(SessionRequest request) {
		logger.info("CLIENT :CachClient - createSession -  START with raw request: {}", request);
		
		SessionResponse response = null;
		Mono<SessionResponse> iResp = null;

		String uri = UriComponentsBuilder.fromHttpUrl(cach0Uri + "/sess/create").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SessionRequest.class)
					.retrieve()
					.bodyToMono(SessionResponse.class);
		}

		catch(Exception e) {
			logger.error("Client : CachClient - createSession - EXCEPTION", e);
			throw new AppException("SSKO-03");
		}
		response = iResp.block();
		
		if(ObjectUtils.isEmpty(response) || response.getInsert() == false) {
			logger.error("Client : CachClient - createSession - EXCEPTION on create");
			throw new AppException("SSKO-03");
		}
		
		logger.info("CLIENT :CachClient - createSession -  END response: {}", response);
		return response;
	}
	
	public SessionResponse updateSession(SessionRequest request) {
		logger.info("CLIENT :CachClient - updateSession -  START with raw request: {}", request);
		
		SessionResponse response = null;
		Mono<SessionResponse> iResp = null;

		String uri = UriComponentsBuilder.fromHttpUrl(cach0Uri + "/sess/update").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SessionRequest.class)
					.retrieve()
					.bodyToMono(SessionResponse.class);
		}

		catch(Exception e) {
			logger.error("Client : CachClient - updateSession - EXCEPTION", e);
			throw new AppException("SSKO-03");
		}
		response = iResp.block();
		
		if(ObjectUtils.isEmpty(response) || response.getInsert() == false) {
			logger.error("Client : CachClient - updateSession - EXCEPTION on update");
			throw new AppException("SSKO-03");
		}
		
		logger.info("CLIENT :CachClient - updateSession -  END response: {}", response);
		return response;
		
	}
	
}

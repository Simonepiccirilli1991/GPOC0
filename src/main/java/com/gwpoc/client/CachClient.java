package com.gwpoc.client;

import java.util.Optional;

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

	public SessionResponse getSession(SessionRequest request) {

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

			throw new AppException("SSKO-03");

		}
		if(ObjectUtils.isEmpty(iResp)) {

			throw new AppException("SSKO-03");
		}

		response = iResp.block();

		return response;
	}
	
	public SessionResponse createSession(SessionRequest request) {
		
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

			throw new AppException("SSKO-03");
		}
		response = iResp.block();
		
		if(ObjectUtils.isEmpty(response) || response.getInsert() == false) {
			throw new AppException("SSKO-03");
		}
		
		return response;
	}
	
	public SessionResponse updateSession(SessionRequest request) {
		
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

			throw new AppException("SSKO-03");
		}
		response = iResp.block();
		
		if(ObjectUtils.isEmpty(response) || response.getInsert() == false) {
			throw new AppException("SSKO-03");
		}
		
		return response;
		
	}
	
}

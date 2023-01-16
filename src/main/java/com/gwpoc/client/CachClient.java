package com.gwpoc.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;

import reactor.core.publisher.Mono;

@Component
public class CachClient {

	@Value("${config.cach0.end-point}")
	private String cach0Uri;
	WebClient webClient = WebClient.create(cach0Uri);
	
	public Optional<SessionResponse> getSession(SessionRequest request) {
		
		Optional<SessionResponse> response = null;
		Mono<SessionResponse> iResp = null;
		
		try {
			iResp = webClient.post()
					.uri("prodotti/transact")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), SessionRequest.class)
					.retrieve()
					.bodyToMono(SessionResponse.class);
		}
		
		catch(Exception e) {
			//TODO implementare gestione errore
		}
		
		//TODO inserire controlli con eccezione 
		if(ObjectUtils.isEmpty(iResp)) {
			
		}
		
		response = Optional.of(iResp.block());
		
		return response;
	}
}

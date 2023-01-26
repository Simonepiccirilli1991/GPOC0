package com.gwpoc.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.AnagraficaRequest;
import com.gwpoc.model.response.AnagraficaResponse;

import reactor.core.publisher.Mono;

@Component
public class AnscClient {

	@Value("${config.asnc0.end-point}")
	private String anscUri;
	
	WebClient webClient = WebClient.create(anscUri);

	// insert anagrafica
	public void insertAnagrafica(AnagraficaRequest request) {

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
		catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException("TODO");
		
	}
	// get anagrafica
	public AnagraficaResponse getAnagrafica(String bt) {

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
			throw new AppException("");
		}
		response = iResp.block();
		
		if(Boolean.TRUE.equals(response.isError()))
				throw new AppException("TODO");
		
		return response;
	}
}

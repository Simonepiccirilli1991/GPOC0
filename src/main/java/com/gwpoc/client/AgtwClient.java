package com.gwpoc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.AnagraficaRequest;
import com.gwpoc.model.request.AuthRequest;
import com.gwpoc.model.response.AnagraficaResponse;

import reactor.core.publisher.Mono;

@Component
public class AgtwClient {

	@Value("${config.agtw0.end-point}")
	private String agtwUri;
	
	Logger logger = LoggerFactory.getLogger(AgtwClient.class);
	
	WebClient webClient = WebClient.create(agtwUri);
	
	// insert anagrafica
		public ResponseEntity<String> createAuth(AuthRequest request) {
			
			logger.info("CLIENT :AgtwClient - createAuth -  START with raw request: {}", request);
			
			ResponseEntity<String> response = null;
			Mono<ResponseEntity<String>> iResp = null;
			
			String uri = UriComponentsBuilder.fromHttpUrl(agtwUri + "/create").toUriString();
			try {
				iResp = webClient.post()
						.uri(uri)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(request), AnagraficaRequest.class)
						.retrieve()
						.toEntity(String.class);
			}
			catch(Exception e) {
				logger.error("Client : AgtwClient - createAuth - EXCEPTION", e);
				throw new AppException("");
			}
			response = iResp.block();
			
			logger.info("CLIENT :AnscClient - insertAnag -  END response: {}", response);
			
			return response;
		}
}

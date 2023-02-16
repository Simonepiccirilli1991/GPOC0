package com.gwpoc.client;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.AnagraficaRequest;
import com.gwpoc.model.request.AuthRequest;
import com.gwpoc.model.response.AnagraficaResponse;

import io.netty.channel.ChannelOption;
import reactor.core.publisher.Mono;

@Component
public class AgtwClient {

	@Value("${config.agtw0.end-point}")
	private String agtwUri;
	Logger logger = LoggerFactory.getLogger(AgtwClient.class);
	
	WebClient webClient = WebClient.create(agtwUri);
	
	// create auth
	public ResponseEntity<Boolean> createAuth(AuthRequest request) {

		logger.info("CLIENT :AgtwClient - createAuth -  START with raw request: {}", request);

		ResponseEntity<Boolean> response = null;
		Mono<ResponseEntity<Boolean>> iResp = null;

		String uri = UriComponentsBuilder.fromHttpUrl(agtwUri + "/create").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), AuthRequest.class)
					.retrieve()
					.toEntity(Boolean.class);
		}
		catch(Exception e) {
			logger.error("Client : AgtwClient - createAuth - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		logger.info("CLIENT :AnscClient - insertAnag -  END response: {}", response);
		
		if(response.getBody() != true)
			throw new AppException("dunno");
		
		return response;
	}

	public void validateAuth(String bt, String pin, boolean doubleAuth, HttpHeaders header) {
		logger.info("CLIENT: AgtwClient - createAuth - START with raw request: {}, {}, {}", bt, pin, doubleAuth);

		String endpoint = doubleAuth ? "/validate/body" : "/validate";
		String uri = UriComponentsBuilder.fromHttpUrl(agtwUri + endpoint).toUriString();

		try {
			AuthRequest request = new AuthRequest();
					if(doubleAuth) {
						request.setBt(bt);
						request.setPin(pin);
					}
			Mono<ResponseEntity<String>> iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", header.getFirst("Authorization"))
					.bodyValue(request)
					.retrieve()
					.toEntity(String.class);

			ResponseEntity<String> response = iResp.block();
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new AppException("HTTP error " + response.getStatusCodeValue());
			}

		} catch (Exception e) {
			logger.error("CLIENT: AgtwClient - createAuth - EXCEPTION", e);
			throw new AppException(e.getMessage());
		}
	}

	public void validateAuthBank(HttpHeaders header) {

		String endpoint = "/validate/bank";
		String uri = UriComponentsBuilder.fromHttpUrl(agtwUri + endpoint).toUriString();

			Mono<ResponseEntity<String>> iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", header.getFirst("Authorization"))
					.retrieve()
					.toEntity(String.class).onErrorMap(
							e -> new AppException("ERKO-08"));

			ResponseEntity<String> response = iResp.block();

			if(response.getBody().equals("Bank user not admitted"))
				throw new AppException("ERKO-09");
	}
}

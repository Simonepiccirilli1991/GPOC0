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

	public void validateAuth(String bt,String pin,Boolean doubleAuth, HttpHeaders header) {
		logger.info("CLIENT :AgtwClient - createAuth -  START with raw request: {} , {} , {}", bt, pin, doubleAuth);

		ResponseEntity<String> response = null;
		Mono<ResponseEntity<String>> iResp = null;

		// passo pin e bt oltre a auth
		if(Boolean.TRUE.equals(doubleAuth)) {

			AuthRequest request = new AuthRequest();
			request.setBt(bt);
			request.setPin(pin);

			String uri = UriComponentsBuilder.fromHttpUrl(agtwUri + "/validate").toUriString();
			try {
				iResp = webClient.post()
						.uri(uri)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", header.getFirst("Authorization"))
						.body(Mono.just(request), AuthRequest.class)
						.retrieve()
						.toEntity(String.class);
			}
			catch(Exception e) {
				logger.error("Client : AgtwClient - createAuth - EXCEPTION", e);
				throw new AppException("");
			}
			//passo solo auth per vedere se e ancora valido
		}else {

			String uri = UriComponentsBuilder.fromHttpUrl(agtwUri + "/validate1").toUriString();
			try {
				iResp = webClient.post()
						.uri(uri)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", header.getFirst("Authorization"))
						.retrieve()
						.toEntity(String.class);
			}
			catch(Exception e) {
				logger.error("Client : AgtwClient - createAuth - EXCEPTION", e);
				throw new AppException("");
			}
		}

		response = iResp.block();

		logger.info("CLIENT :AnscClient - insertAnag -  END response: {}", response);
		if(ObjectUtils.isEmpty(response.getBody()) || !response.getBody().equals("Action performed successfully!"))
			throw new AppException("TBD");
	}
}

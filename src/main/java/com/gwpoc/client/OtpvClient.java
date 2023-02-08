package com.gwpoc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.CheckOtpRequest;
import com.gwpoc.fragment.model.CheckOtpResponse;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.model.response.GenerateOtpResponse;

import reactor.core.publisher.Mono;

@Component
public class OtpvClient {

	@Value("${config.otpv0.end-point}")
	private String otpvUri;
	
	WebClient webClient = WebClient.create(otpvUri);
	
	Logger logger = LoggerFactory.getLogger(OtpvClient.class);
	
	// genera otpv
	public GenerateOtpResponse generaOtp(GenerateOtpRequest request) {
		logger.info("CLIENT :OtpvClient - generaOtp -  START with raw request: {}", request);
		GenerateOtpResponse response = null;
		Mono<GenerateOtpResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/v1/generateOtp").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), GenerateOtpRequest.class)
					.retrieve()
					.bodyToMono(GenerateOtpResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : OtpvClient - generaOtp - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();

		if(response.getOtpSend() == false)
			throw new AppException("TODO");
		
		logger.info("CLIENT :OtpvClient - generaOtp -  END response: {}", response);
		return response;
	}
	
	// genera otpv mockato
	public GenerateOtpResponse generaOtpMock(GenerateOtpRequest request) {
		logger.info("CLIENT :OtpvClient - generaOtpMock -  START with raw request: {}", request);
		
		GenerateOtpResponse response = null;
		Mono<GenerateOtpResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/v1/mock/generateOtp").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), GenerateOtpRequest.class)
					.retrieve()
					.bodyToMono(GenerateOtpResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : OtpvClient - generaOtpMock - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(response.getOtpSend() == false)
			throw new AppException("TODO");
		
		logger.info("CLIENT :OtpvClient - generaOtpMock -  END response: {}", response);
		return response;
	}
	
	// checkOtp
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {
		logger.info("CLIENT :OtpvClient - checkOtp -  START with raw request: {}", request);
		
		CheckOtpResponse response = null;
		Mono<CheckOtpResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/v1/checkotp").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), CheckOtpRequest.class)
					.retrieve()
					.bodyToMono(CheckOtpResponse.class);
		}
		catch(Exception e) {
			logger.error("Client : OtpvClient - checkOtp - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();
		
		if(response.getAutenticationSucc() != true)
			throw new AppException("TODO");
		
		logger.info("CLIENT :OtpvClient - checkOtp -  END response: {}", response);
		return response;
	}
}

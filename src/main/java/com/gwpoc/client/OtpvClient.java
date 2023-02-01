package com.gwpoc.client;

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

	// genera otpv
	public GenerateOtpResponse generaOtp(GenerateOtpRequest request) {

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
			throw new AppException("");
		}
		response = iResp.block();

		if(response.getOtpSend() == false)
			throw new AppException("TODO");
		
		return response;
	}
	
	// genera otpv mockato
	public GenerateOtpResponse generaOtpMock(GenerateOtpRequest request) {

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
			throw new AppException("");
		}
		response = iResp.block();
		
		if(response.getOtpSend() == false)
			throw new AppException("TODO");
		
		return response;
	}
	
	// checkOtp
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {
		
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
			throw new AppException("");
		}
		response = iResp.block();
		
		if(response.getAutenticationSucc() != true)
			throw new AppException("TODO");
		
		return response;
	}
}

package com.gwpoc.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.gwpoc.error.AppException;
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.model.request.PushRequest;
import com.gwpoc.model.response.GenerateOtpResponse;
import com.gwpoc.model.response.PushResponse;

import reactor.core.publisher.Mono;

public class OtpvPushClient {

	@Value("${config.otpv0.end-point}")
	private String otpvUri;
	
	WebClient webClient = WebClient.create(otpvUri);

	//SEND push
	public PushResponse sendPush(PushRequest request) {

		PushResponse response = null;
		Mono<PushResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/push/insert").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushRequest.class)
					.retrieve()
					.bodyToMono(PushResponse.class);
		}
		catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();

		if(ObjectUtils.isEmpty(response)|| response.getSended() == false)
			throw new AppException("TODO");
		
		return response;
	}
	
	//ACCEPT push
	public PushResponse acceptPush(PushRequest request) {

		PushResponse response = null;
		Mono<PushResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/push/accept").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushRequest.class)
					.retrieve()
					.bodyToMono(PushResponse.class);
		}
		catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();

		if(ObjectUtils.isEmpty(response)|| response.getAcepted() == false)
			throw new AppException("TODO");
		
		return response;
	}
	//GET push status
	public PushResponse getStatusPush(PushRequest request) {

		PushResponse response = null;
		Mono<PushResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/push/get").toUriString();
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushRequest.class)
					.retrieve()
					.bodyToMono(PushResponse.class);
		}
		catch(Exception e) {
			throw new AppException("");
		}
		response = iResp.block();

		if(ObjectUtils.isEmpty(response)|| ObjectUtils.isEmpty(response.getStatus()))
			throw new AppException("TODO");
		
		return response;
	}
}

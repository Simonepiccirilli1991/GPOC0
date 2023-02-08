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
import com.gwpoc.fragment.model.GenerateOtpRequest;
import com.gwpoc.model.request.PushRequest;
import com.gwpoc.model.response.GenerateOtpResponse;
import com.gwpoc.model.response.PushResponse;

import reactor.core.publisher.Mono;

@Component
public class OtpvPushClient {

	@Value("${config.otpv0.end-point}")
	private String otpvUri;
	
	WebClient webClient = WebClient.create(otpvUri);

	Logger logger = LoggerFactory.getLogger(OtpvPushClient.class);
	
	//SEND push
	public PushResponse sendPush(PushRequest request) {
		logger.info("CLIENT :OtpvPushClient - sendPush -  START with raw request: {}", request);
		
		PushResponse response = null;
		Mono<PushResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/push/send").toUriString();
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
			logger.error("Client : OtpvPushClient - sendPush - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();

		if(ObjectUtils.isEmpty(response)|| response.getSended() == false)
			throw new AppException("TODO");
		
		logger.info("CLIENT :OtpvPushClient - sendPush -  END response: {}", response);
		
		return response;
	}
	
	//ACCEPT push
	public PushResponse acceptPush(PushRequest request) {
		logger.info("CLIENT :OtpvPushClient - acceptPush -  START with raw request: {}", request);
		
		PushResponse response = null;
		Mono<PushResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(otpvUri + "/push/confirm").toUriString();
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
			logger.error("Client : acceptPush - sendPush - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();

		if(ObjectUtils.isEmpty(response)|| ObjectUtils.isEmpty(response.getAcepted()) || response.getAcepted() == false)
			throw new AppException("TODO");
		
		logger.info("CLIENT :OtpvPushClient - acceptPush -  END response: {}", response);
		return response;
	}
	//GET push status
	public PushResponse getStatusPush(PushRequest request) {
		logger.info("CLIENT :OtpvPushClient - getStatusPush -  START with raw request: {}", request);
		
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
			logger.error("Client : OtpvPushClient - getStatusPush - EXCEPTION", e);
			throw new AppException("");
		}
		response = iResp.block();

		if(ObjectUtils.isEmpty(response)|| ObjectUtils.isEmpty(response.getStatus()))
			throw new AppException("TODO");
		
		logger.info("CLIENT :OtpvPushClient - getStatusPush -  END response: {}", response);
		return response;
	}
}

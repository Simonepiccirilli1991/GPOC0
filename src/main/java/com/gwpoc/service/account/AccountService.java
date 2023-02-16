package com.gwpoc.service.account;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.cach.SessionChService;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.AccountResponse;

@Service
public class AccountService {

	@Autowired
	IwdbClient iwdbClient;
	@Autowired
	SessionChService sessionChService;
	
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Retryable(retryFor ={TimeoutException.class}, maxAttempts = 3)
	public AccountResponse insertAccount(AccountRequest request) {
		
		logger.info("API :AccountService - insert -  START with raw request: {}", request);
		
		AccountResponse response = new AccountResponse();
		
		AccountIwResponse iResp = iwdbClient.insertAccount(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :AccountService - insert - EXCEPTION cause by anagrafica : {}", iResp);
			throw new AppException("ERKO-02");
		}
		response.setAccount(iResp.getAccount());
		response.setDone(true);
		logger.info("API :AccountService - insert - END with response: {}", response);
		
		return response;
	}
	
	public AccountResponse getAccount(String bt) {
		
		logger.info("API :AccountService - get -  START with raw request: {}", bt);
		
		AccountResponse response = new AccountResponse();
		
		AccountIwResponse iResp = iwdbClient.getAcc(bt);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :AccountService - insert - EXCEPTION cause by anagrafica : {}", iResp);
			throw new AppException("ERKO-02");
		}
		response.setAccount(iResp.getAccount());
		response.setDone(true);
		
		logger.info("API :AccountService - get - END with response: {}", response);
		return response;
	}
	
	public AccountResponse updateAccount(AccountRequest request) {
		
		logger.info("API :AccountService - update -  START with raw request: {}", request);
		
		AccountResponse response = new AccountResponse();
		
		SessionRequest sessRequest = new SessionRequest();
		sessRequest.setBt(request.getBt());
		// controllo che sessione sia in l2
		if(!sessionChService.checkL2(sessRequest)) {
			throw new AppException("ERKO-10");
		}
		
		AccountIwResponse iResp = iwdbClient.updateAcc(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :AccountService - update - EXCEPTION cause by anagrafica : {}", iResp);
			throw new AppException("ERKO-02");
		}
		response.setAccount(iResp.getAccount());
		response.setDone(true);
		logger.info("API :AccountService - update - END with response: {}", response);
		
		return response;
	}
}

package com.gwpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.response.AccountResponse;

@Service
public class AccountService {

	@Autowired
	IwdbClient iwdbClient;
	
	public AccountResponse insertAccount(AccountRequest request) {
		
		AccountResponse response = new AccountResponse();
		
		AccountIwResponse iResp = iwdbClient.insertAccount(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.getIsError())
			throw new AppException("ERKO-02");
		
		response.setAccount(iResp.getAccount());
		response.setDone(true);
				
		return response;
	}
	
	public AccountResponse getAccount(String bt) {
		
		AccountResponse response = new AccountResponse();
		
		AccountIwResponse iResp = iwdbClient.getAcc(bt);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.getIsError())
			throw new AppException("ERKO-02");
		
		response.setAccount(iResp.getAccount());
		response.setDone(true);
		
		return response;
	}
	
	public AccountResponse updateAccount(AccountRequest request) {
		
		AccountResponse response = new AccountResponse();
		AccountIwResponse iResp = iwdbClient.updateAcc(request);
		
		if(ObjectUtils.isEmpty(iResp) || iResp.getIsError())
			throw new AppException("ERKO-02");
		
		response.setAccount(iResp.getAccount());
		response.setDone(true);
		
		return response;
	}
}

package com.gwpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.OrdiniIwResponse;
import com.gwpoc.fragment.model.Ordini;
import com.gwpoc.model.request.OrdiniRequest;

@Service
public class OrdiniService {
	
	@Autowired
	IwdbClient iwdbClient;
	
	Logger logger = LoggerFactory.getLogger(OrdiniService.class);
	
	public Ordini creaOrdine(OrdiniRequest request) {

		logger.info("API :OrdiniService - create -  START with raw request: {}", request);
		Ordini response = null;

		OrdiniIwResponse iResp = iwdbClient.creaOrdine(request);

		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :OrdiniService - create - EXCEPTION cause by iwdb : {}", iResp);
			throw new AppException("SSKO-10");
		}
		response = iResp.getOrdine();
		logger.info("API :OrdiniService - create - END with response: {}", response);
		
		return response;
	}

	public Ordini getOrdine(OrdiniRequest request) {
		
		logger.info("API :OrdiniService - get -  START with raw request: {}", request);
		
		Ordini response = null;

		OrdiniIwResponse iResp = iwdbClient.getOrder(request);

		if(ObjectUtils.isEmpty(iResp) || iResp.isError()) {
			logger.error("Client :OrdiniService - get - EXCEPTION cause by iwdb : {}", iResp);
			throw new AppException("SSKO-10");
		}
		response = iResp.getOrdine();
		logger.info("API :OrdiniService - get - END with response: {}", response);
		
		return response;
	}
}

package com.gwpoc.service;

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
	
	public Ordini creaOrdine(OrdiniRequest request) {

		Ordini response = null;

		OrdiniIwResponse iResp = iwdbClient.creaOrdine(request);

		if(ObjectUtils.isEmpty(iResp) || iResp.isError())
			throw new AppException("SSKO-10");

		response = iResp.getOrdine();

		return response;
	}

	public Ordini getOrdine(OrdiniRequest request) {

		Ordini response = null;

		OrdiniIwResponse iResp = iwdbClient.getOrder(request);

		if(ObjectUtils.isEmpty(iResp) || iResp.isError())
			throw new AppException("SSKO-10");

		response = iResp.getOrdine();

		return response;
	}
}

package com.gwpoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.Util.ConstEnum;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.StatusResponse;
import com.gwpoc.service.otp.CheckOtpService;
import com.gwpoc.service.otp.GenerateOtpService;

@Service
public class StatusService {

	@Autowired
	IwdbClient iwdb;
	@Autowired
	AnagraficaService anagrafica;
	
	Logger logger = LoggerFactory.getLogger(StatusService.class);
	
	public StatusResponse getStatus(String bt) {
		logger.info("API :StatusService - getStatus -  START with raw request: {}", bt);
		
		StatusResponse response = new StatusResponse();
		
		// chiamo status iwdb, e vedo cosa torna se torna che utente e gia registrato faccio controllo incorciato cona anagrafica
		StatusIwResponse iResp = iwdb.getstatus(bt);
		
		if(ObjectUtils.isEmpty(iResp)) {
			logger.error("Client :StatusService - getStatus - EXCEPTION cause by status");
			throw new AppException("TBD");
		}
		switch (iResp.getStatus()) {
		// chiama alla status di wiam
			case ConstEnum.UTENTE_NOFOUND :
				response.setMsg(iResp.getMsg());
				response.setStatus(ConstEnum.UTENTE_NOFOUND);
				response.setAction(null);
				logger.info("API :StatusService - getStatus - END with response: {}", response);
				return response;
				
			
			case ConstEnum.ACCOUNT_NOFOUND:
				response.setMsg(iResp.getMsg());
				response.setStatus(ConstEnum.ACCOUNT_NOFOUND);
				// controllo se ha mail certificata, in base a quello torno action
				response.setAction(setAction(bt));
				logger.info("API :StatusService - getStatus - END with response: {}", response);
				return response;
				
			case ConstEnum.REGISTRATION_FOUND:
				response.setMsg(iResp.getMsg());
				response.setStatus(ConstEnum.REGISTRATION_FOUND);
				response.setAction(ActionEnum.CONSENT);
				logger.info("API :StatusService - getStatus - END with response: {}", response);
				return response;
				
				
		default:
			logger.error("Client :StatusService - getStatus - EXCEPTION cause by unknow status");
			throw new AppException("Unknow Status");
		}
		
	}
	
	private ActionEnum setAction(String bt) {
		
		AnagraficaResponse response = anagrafica.getAnagrafica(bt);
		
		if(ObjectUtils.isEmpty(response)) {
			logger.error("Client :StatusService - getStatus - EXCEPTION cause by unable to retrive status");
			throw new AppException("Error on retrive anagrafica");
		}
		
		if(response.getMailCertificata())
			return ActionEnum.REGISTERACCOUNT;
		else
			return ActionEnum.SENDOTPCERTIFY;
	}
}

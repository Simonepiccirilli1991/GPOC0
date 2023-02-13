package com.gwpoc.Service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.client.AgtwClient;
import com.gwpoc.client.AnscClient;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.client.OtpvClient;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
import com.gwpoc.fragment.model.CheckOtpResponse;
import com.gwpoc.model.request.OtpRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.GenerateOtpResponse;
import com.gwpoc.model.response.OtpResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.otp.CheckOtpService;
import com.gwpoc.service.otp.GenerateOtpService;

@SpringBootTest
public class OtpServiceTest {

	@Autowired
	GenerateOtpService generate;
	@Autowired
	CheckOtpService check;
	@MockBean
	CachClient cachClient;
	@MockBean
	AnscClient anscClient;
	@MockBean
	OtpvClient otpvClient;
	@MockBean
	IwdbClient iwdbCLient;
	@MockBean
	AgtwClient agtw;
	
	@Test
	public void generateTestOK() {
		
		OtpRequest request = new OtpRequest();
		request.setBt("bt");
		request.setEmail("email");
		
		SessionResponse session = new SessionResponse();
		session.setScope("l1");
		session.setValid(true);
		
		AnagraficaResponse anagrafica = new AnagraficaResponse();
		anagrafica.setMail("mail");

		GenerateOtpResponse otp = new GenerateOtpResponse();
		otp.setTrxId("trxId");
		
		when(cachClient.getSession(any())).thenReturn(session);
		
		when(anscClient.getAnagrafica(any())).thenReturn(anagrafica);
		
		when(otpvClient.generaOtpMock(any())).thenReturn(otp);
		
		OtpResponse response = generate.lunchService(request, null);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CHECKOTP);
		assertThat(response.getTrxId()).isEqualTo("trxId");
		
		
	}
	
	@Test
	public void checkOtpTestOK() {
		
		OtpRequest request = new OtpRequest();
		request.setBt("bt");
		request.setEmail("email");
		request.setOtp("otp");
		request.setTrxId("trxId");
		
		CheckOtpResponse otp = new CheckOtpResponse();
		otp.setAutenticationSucc(true);
		
		SessionResponse session = new SessionResponse();
		session.setScope("l2");
		session.setValid(true);
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setMailCertificata(true);
		
		StatusIwResponse statusResp = new StatusIwResponse();
		statusResp.setMsg("daje");
		statusResp.setStatus("registered");
		
		HttpHeaders header = new HttpHeaders();
		header.add("Prova", "unCazz");
		
		HttpHeaders headerResp = new HttpHeaders();
		headerResp.add("Authorization", "1234567");
		
		when(iwdbCLient.getstatus(any())).thenReturn(statusResp);
		
		when(anscClient.getAnagrafica(any())).thenReturn(anag);
		
		when(otpvClient.checkOtp(any())).thenReturn(otp);
		
		when(cachClient.updateSession(any())).thenReturn(session);
		
		doNothing().when(agtw).validateAuth(any(),any(),any(),any());
		
		OtpResponse response = check.lunchService(request, headerResp);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CONSENT);
		
	}
}

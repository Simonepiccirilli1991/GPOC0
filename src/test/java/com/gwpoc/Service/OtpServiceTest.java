package com.gwpoc.Service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.client.AnscClient;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.OtpvClient;
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
		
		when(otpvClient.checkOtp(any())).thenReturn(otp);
		
		when(cachClient.updateSession(any())).thenReturn(session);
		
		OtpResponse response = check.lunchService(request, null);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CONSENT);
		
	}
}

package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.client.AgtwClient;
import com.gwpoc.client.AnscClient;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.client.OtpvClient;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.GenerateOtpResponse;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.pin.CertifyMailService;
import com.gwpoc.service.pin.CheckPinService;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
public class PinServiceTest {

	@Autowired
	CertifyMailService certifyMail;
	@Autowired
	CheckPinService checkPinService;
	@MockBean
	AnscClient ansc;
	@MockBean
	CachClient cach;
	@MockBean
	OtpvClient otpv;
	@MockBean
	IwdbClient iwdb;
	@MockBean
	AgtwClient agtw;
	
	@Test
	public void checkPinTestOK() {
		
		PinRequest request = new PinRequest();
		request.setBt("bt");
		request.setOtp("otp");
		request.setPin("pin");
		request.setPin("pin");
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setBancaId("badnaId");
		anag.setMailCertificata(false);
		
		SessionResponse session = new SessionResponse();
		session.setInsert(true);
		
		GenerateOtpResponse otp = new GenerateOtpResponse();
		otp.setTrxId("trxId");
		
		HttpHeaders header = new HttpHeaders();
		header.add("Prova", "unCazz");
		
		HttpHeaders headerResp = new HttpHeaders();
		headerResp.add("Authorization", "1234567");
		
		ResponseEntity<Boolean> resp = new ResponseEntity<>(true,headerResp,HttpStatus.OK);
		doNothing().when(ansc).checkPin(any());
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(cach.createSession(any())).thenReturn(session);
		
		when(otpv.generaOtpMock(any())).thenReturn(otp);
		
		when(agtw.createAuth(any())).thenReturn(resp);
		
		PinResponse response = checkPinService.lunchService(request, header);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CERTIFYMAIL);
		assertThat(response.getTrxId()).isEqualTo("trxId");
		
	}
	
	@Test
	public void checkPinTest2OK() {
		
		PinRequest request = new PinRequest();
		request.setBt("bt");
		request.setOtp("otp");
		request.setPin("pin");
		request.setPin("pin");
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setBancaId("badnaId");
		anag.setMailCertificata(true);
		
		SessionResponse session = new SessionResponse();
		session.setInsert(true);
		
		GenerateOtpResponse otp = new GenerateOtpResponse();
		otp.setTrxId("trxId");
		HttpHeaders header = new HttpHeaders();
		header.add("Prova", "unCazz");
		
		HttpHeaders headerResp = new HttpHeaders();
		headerResp.add("Authorization", "1234567");
		ResponseEntity<Boolean> resp = new ResponseEntity<>(true,headerResp,HttpStatus.OK);
		
		doNothing().when(ansc).checkPin(any());
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(cach.createSession(any())).thenReturn(session);
		
		when(otpv.generaOtpMock(any())).thenReturn(otp);
		
		when(agtw.createAuth(any())).thenReturn(resp);
		
		PinResponse response = checkPinService.lunchService(request, header);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.SENDOTP);
		
	}
	
	@Test
	public void certifyMailTestOK() {
		
		PinRequest request = new PinRequest();
		request.setOtp("bt");
		request.setBt("bt");
		request.setTrxId("trxId");
		
		SessionResponse session = new SessionResponse();
		session.setBt("bt");
		session.setScope("adad");
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setMailCertificata(true);
		
		StatusIwResponse statusResp = new StatusIwResponse();
		statusResp.setMsg("daje");
		statusResp.setStatus("registered");
		
		HttpHeaders header = new HttpHeaders();
		header.add("Prova", "unCazz");
		
		HttpHeaders headerResp = new HttpHeaders();
		headerResp.add("Authorization", "1234567");
		
		ResponseEntity<String> resp = new ResponseEntity<>("Action performed successfully!",header,HttpStatus.OK);
		
		when(iwdb.getstatus(any())).thenReturn(statusResp);
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(cach.getSession(any())).thenReturn(session);
		
		doNothing().when(ansc).certifyMail(any());
		
		doNothing().when(agtw).validateAuth(any(),any(),any(),any());
		
		PinResponse response = certifyMail.lunchService(request, headerResp);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CONSENT);
	}
}

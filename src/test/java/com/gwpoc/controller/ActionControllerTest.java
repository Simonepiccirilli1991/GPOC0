package com.gwpoc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwpoc.Util.ActionEnum;
import com.gwpoc.client.AnscClient;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.client.OtpvClient;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
import com.gwpoc.fragment.model.CheckOtpResponse;
import com.gwpoc.model.request.OtpRequest;
import com.gwpoc.model.request.PinRequest;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.GenerateOtpResponse;
import com.gwpoc.model.response.OtpResponse;
import com.gwpoc.model.response.PinResponse;
import com.gwpoc.model.response.SessionResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class ActionControllerTest {

	
	@Autowired
	MockMvc mvc;
	@MockBean
	AnscClient ansc;
	@MockBean 
	OtpvClient otpv;
	@MockBean
	CachClient cach;
	@MockBean
	IwdbClient iwdb;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void checkPinTest1OK() throws  Exception {
		
		PinRequest request = new PinRequest();
		request.setBt("bt");
		request.setOtp("otp");
		request.setPin("pin");
		request.setPin("pin");
		request.setAction(ActionEnum.CHECKPIN);
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setBancaId("badnaId");
		anag.setMailCertificata(false);
		
		SessionResponse session = new SessionResponse();
		session.setInsert(true);
		
		GenerateOtpResponse otp = new GenerateOtpResponse();
		otp.setTrxId("trxId");
		doNothing().when(ansc).checkPin(any());
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(cach.createSession(any())).thenReturn(session);
		
		when(otpv.generaOtpMock(any())).thenReturn(otp);
		
		String resp = mvc.perform(post("/action/pin")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		PinResponse response = mapper.readValue(resp, PinResponse.class);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CERTIFYMAIL);
		assertThat(response.getTrxId()).isEqualTo("trxId");
	}
	
	@Test
	public void checkPinTest2OK() throws Exception {
		
		PinRequest request = new PinRequest();
		request.setBt("bt");
		request.setOtp("otp");
		request.setPin("pin");
		request.setPin("pin");
		request.setAction(ActionEnum.CHECKPIN);
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setBancaId("badnaId");
		anag.setMailCertificata(true);
		
		SessionResponse session = new SessionResponse();
		session.setInsert(true);
		
		GenerateOtpResponse otp = new GenerateOtpResponse();
		otp.setTrxId("trxId");
		doNothing().when(ansc).checkPin(any());
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(cach.createSession(any())).thenReturn(session);
		
		when(otpv.generaOtpMock(any())).thenReturn(otp);
		
		String resp = mvc.perform(post("/action/pin")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		PinResponse response = mapper.readValue(resp, PinResponse.class);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.SENDOTP);
		
	}
	
	@Test
	public void certifyMailTestOK() throws Exception {
		
		PinRequest request = new PinRequest();
		request.setOtp("bt");
		request.setBt("bt");
		request.setTrxId("trxId");
		request.setAction(ActionEnum.CERTIFYMAIL);
		
		SessionResponse session = new SessionResponse();
		session.setBt("bt");
		session.setScope("adad");
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setMailCertificata(true);
		
		StatusIwResponse statusResp = new StatusIwResponse();
		statusResp.setMsg("daje");
		statusResp.setStatus("registered");
		
		when(iwdb.getstatus(any())).thenReturn(statusResp);
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(cach.getSession(any())).thenReturn(session);
		
		doNothing().when(ansc).certifyMail(any());
		
		String resp = mvc.perform(post("/action/pin")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		PinResponse response = mapper.readValue(resp, PinResponse.class);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CONSENT);
	}
	
	//---------------------OTP TEST ----------------------------------------------//
	@Test
	public void generateOtpTestOK() throws Exception {
		
		OtpRequest request = new OtpRequest();
		request.setBt("bt");
		request.setEmail("email");
		request.setAction(ActionEnum.SENDOTP);
		
		SessionResponse session = new SessionResponse();
		session.setScope("l1");
		session.setValid(true);
		
		AnagraficaResponse anagrafica = new AnagraficaResponse();
		anagrafica.setMail("mail");

		GenerateOtpResponse otp = new GenerateOtpResponse();
		otp.setTrxId("trxId");
		
		when(cach.getSession(any())).thenReturn(session);
		
		when(ansc.getAnagrafica(any())).thenReturn(anagrafica);
		
		when(otpv.generaOtpMock(any())).thenReturn(otp);
		
		String resp = mvc.perform(post("/action/otp")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		OtpResponse response = mapper.readValue(resp, OtpResponse.class);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CHECKOTP);
		assertThat(response.getTrxId()).isEqualTo("trxId");
	}
	
	@Test
	public void checkOtpTestOK() throws Exception {
		
		OtpRequest request = new OtpRequest();
		request.setBt("bt");
		request.setEmail("email");
		request.setOtp("otp");
		request.setTrxId("trxId");
		request.setAction(ActionEnum.CHECKOTP);
		
		SessionResponse session = new SessionResponse();
		session.setScope("l2");
		session.setValid(true);
		
		CheckOtpResponse otp = new CheckOtpResponse();
		otp.setAutenticationSucc(true);
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setMailCertificata(true);
		
		StatusIwResponse statusResp = new StatusIwResponse();
		statusResp.setMsg("daje");
		statusResp.setStatus("registered");
		
		when(iwdb.getstatus(any())).thenReturn(statusResp);
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		when(otpv.checkOtp(any())).thenReturn(otp);
		
		when(cach.updateSession(any())).thenReturn(session);
		
		String resp = mvc.perform(post("/action/otp")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		OtpResponse response = mapper.readValue(resp, OtpResponse.class);
		
		assertThat(response.getAction()).isEqualTo(ActionEnum.CONSENT);
	}
}

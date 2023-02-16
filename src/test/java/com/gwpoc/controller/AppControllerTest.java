package com.gwpoc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.gwpoc.client.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwpoc.Util.ActionEnum;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.fragment.iwdb.OrdiniIwResponse;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.fragment.model.Account;
import com.gwpoc.fragment.model.Ordini;
import com.gwpoc.fragment.model.Utente;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.request.OrdiniRequest;
import com.gwpoc.model.request.PushRequest;
import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.AccountResponse;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.PushResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.model.response.StatusResponse;
import com.gwpoc.model.response.UtenteResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {
	
	@MockBean
	OtpvPushClient pushCLient;
	@MockBean
	AnscClient anscClient;
	@MockBean
	IwdbClient iwdbClient;
	@MockBean
	CachClient cachClient;
	@MockBean
	AgtwClient agtwClient;
	@Autowired
	MockMvc mvc;

	ObjectMapper mapper = new ObjectMapper();

// -------- UTENTE TEST ---------------------------------------------------------------------------------//
	@Test
	public void registraUtenteTestOK() throws Exception {

		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");

		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCodiceEsito("00");

		when(iwdbClient.registraUt(any())).thenReturn(iResp);
		
		doNothing().when(anscClient).insertAnagrafica(any());
		
		String resp = mvc.perform(post("/app/utente/register")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		UtenteResponse response = mapper.readValue(resp, UtenteResponse.class);

		assertThat(response.getBt()).isEqualTo("bt");
		assertThat(response.getRegisteredUpdated()).isEqualTo(true);
	}

	@Test
	public void updateUtenteTestOK() throws Exception {

		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");

		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCodiceEsito("00");

		SessionResponse sessResp = new SessionResponse();
		sessResp.setBt("bt");
		sessResp.setScope("l2");
		sessResp.setValid(true);


		when(cachClient.getSession(any())).thenReturn(sessResp);

		when(iwdbClient.updateUtente(any())).thenReturn(iResp);

		String resp = mvc.perform(post("/app/utente/update")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		UtenteResponse response = mapper.readValue(resp, UtenteResponse.class);

		assertThat(response.getMsg()).isEqualTo("Dati aggiornati con successo");
		assertThat(response.getRegisteredUpdated()).isEqualTo(true);
	}

	@Test
	public void getUtenteTestOK() throws Exception {

		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCodiceEsito("00");

		Utente utente = new Utente();
		utente.setBt("bt");
		utente.setCf("cf");
		utente.setId(1);
		utente.setUsername("usr");
		utente.setChannel("ch");
		
		iResp.setUtente(utente);
		
		when(iwdbClient.getUtente(any())).thenReturn(iResp);

		String resp = mvc.perform(post("/app/utente/get")
				.contentType("application/json")
				.content(mapper.writeValueAsString("bt")))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		UtenteResponse response = mapper.readValue(resp, UtenteResponse.class);

		assertThat(response.getUtente().getBt()).isEqualTo("bt");
		assertThat(response.getUtente().getId()).isEqualTo(1);
	}
	
	@Test
	public void getSessionTestKO() throws Exception {
		
		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");
		
		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCodiceEsito("00");
		
		SessionResponse sessResp = new SessionResponse();
		sessResp.setBt("bt");
		sessResp.setScope("l1");
		sessResp.setValid(true);
		
		
		when(cachClient.getSession(any())).thenReturn(sessResp);
		
		when(iwdbClient.updateUtente(any())).thenReturn(iResp);
		
		mvc.perform(post("/app/utente/update")
				.contentType("application/json")
				.content(mapper.writeValueAsString("bt")))
				.andExpect(status().isBadRequest());
		 


	}
	
// -------------Account Test ----------------------------------------------------------------------------//
	
	@Test
	public void insertAcc() throws Exception {
		
		AccountRequest request = new AccountRequest();
		request.setBt("bt");
		request.setDebito(0.00);
		request.setImporto(100.00);
		request.setTipoAccount("Debito");
		
		AccountIwResponse iResp = new AccountIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setAccount(new Account());
		
		when(iwdbClient.insertAccount(any())).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/acc/register")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		AccountResponse response = mapper.readValue(resp, AccountResponse.class);
		
		assertThat(response.isDone()).isTrue();
	}
	
	@Test
	public void getAcc() throws Exception {
		
		Account acc = new Account();
		acc.setCodiceconto("1231231");
		acc.setDebito(0.00);
		acc.setId(1);
		acc.setSaldoattuale(100.00);
		acc.setTipoConto("Debit");
		
		AccountIwResponse iResp = new AccountIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setAccount(acc);
		
		when(iwdbClient.getAcc(any())).thenReturn(iResp);
		
		String resp = mvc.perform(get("/app/acc/get/bt")
				.contentType("application/json"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		AccountResponse response = mapper.readValue(resp, AccountResponse.class);
		
		assertThat(response.isDone()).isTrue();
		assertThat(response.getAccount().getCodiceconto()).isEqualTo("1231231");
	}
	
	@Test
	public void updateAccountTestOK() throws Exception {
		
		AccountRequest request = new AccountRequest();
		request.setBt("bt");
		request.setDebito(0.00);
		request.setImporto(100.00);
		request.setTipoAccount("Debito");
		
		Account acc = new Account();
		acc.setCodiceconto("1231231");
		acc.setDebito(0.00);
		acc.setId(1);
		acc.setSaldoattuale(100.00);
		acc.setTipoConto("Debit");
		
		AccountIwResponse iResp = new AccountIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setAccount(acc);
		iResp.setError(false);
		
		SessionResponse sessResp = new SessionResponse();
		sessResp.setBt("bt");
		sessResp.setScope("l2");
		sessResp.setValid(true);
		
		when(cachClient.getSession(any())).thenReturn(sessResp);
		
		when(iwdbClient.updateAcc(any())).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/acc/update")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		AccountResponse response = mapper.readValue(resp, AccountResponse.class);
		
		assertThat(response.isDone()).isTrue();
		assertThat(response.getAccount().getCodiceconto()).isEqualTo("1231231");
	}

	@Test
	public void rechargeAccTestOK() throws Exception{

		AccountRequest request = new AccountRequest();
		request.setBt("bt");
		request.setImporto(100.00);
		request.setCodiceConto("codiceConto");
		request.setTipoAccount("Debito");
		request.setDebito(0.00);

		SessionResponse sessionResponse = new SessionResponse();
		sessionResponse.setScope("l2");

		AccountIwResponse accountIwResponse = new AccountIwResponse();
		accountIwResponse.setCodiceEsito("OK");

		when(cachClient.getSession(any())).thenReturn(sessionResponse);

		doNothing().when(agtwClient).validateAuthBank(any());
		when(iwdbClient.rechargeAcc(any())).thenReturn(accountIwResponse);

		String resp = mvc.perform(post("/app/acc/recharge")
						.contentType("application/json")
						.header("Authorization", "Bearer " + "token")
						.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		AccountResponse response = mapper.readValue(resp, AccountResponse.class);

		Assertions.assertThat(response.isDone()).isTrue();


	}
	// -------------Order Test ----------------------------------------------------------------------------//
	
	@Test
	public void createOrderTestOK() throws Exception {
		
		OrdiniRequest request = new OrdiniRequest();
		request.setBtAcquirente("asasd");
		request.setBtRicev("dsaad");
		request.setCodiceProd("111");
		request.setCosto(100.00);
		
		Ordini ordine = new Ordini();
		ordine.setBtAcquirente("asasd");
		ordine.setBtRicev("dsaad");
		ordine.setCodiceProd("111");
		ordine.setCosto(100.00);
		ordine.setStatus("status");
		ordine.setId(1);
		
		OrdiniIwResponse iResp = new OrdiniIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setOrdine(ordine);
		
		when(iwdbClient.creaOrdine(any())).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/ord/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		Ordini response = mapper.readValue(resp, Ordini.class);
		
		assertThat(response.getBtAcquirente()).isEqualTo("asasd");
		
		
	}
	
	@Test
	public void getOrderTestOK() throws Exception {
		
		OrdiniRequest request = new OrdiniRequest();
		request.setBtAcquirente("asasd");
		request.setBtRicev("dsaad");
		request.setCodiceProd("111");
		request.setCosto(100.00);
		
		Ordini ordine = new Ordini();
		ordine.setBtAcquirente("asasd");
		ordine.setBtRicev("dsaad");
		ordine.setCodiceProd("111");
		ordine.setCosto(100.00);
		ordine.setStatus("status");
		ordine.setId(1);
		
		OrdiniIwResponse iResp = new OrdiniIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setOrdine(ordine);
		
		when(iwdbClient.getOrder(any())).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/ord/get")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		Ordini response = mapper.readValue(resp, Ordini.class);
		
		assertThat(response.getBtAcquirente()).isEqualTo("asasd");
		
		
	}
	//-------------------status test controller -------------------------------------------//
	
	@Test
	public void getStatusTestOK() throws Exception {
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setMailCertificata(false);
		
		StatusIwResponse statusResp = new StatusIwResponse();
		statusResp.setMsg("daje");
		statusResp.setStatus("reguster_ac");
		
		when(iwdbClient.getstatus(any())).thenReturn(statusResp);
		
		when(anscClient.getAnagrafica(any())).thenReturn(anag);
		
		String resp = mvc.perform(post("/app/status")
				.contentType("application/json")
				.content("bt"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		StatusResponse response = mapper.readValue(resp, StatusResponse.class);
		
		assertThat(response.getMsg()).isEqualTo("daje");
		assertThat(response.getAction()).isEqualTo(ActionEnum.SENDOTPCERTIFY);
	}
	
	//--------------------------- push test controller
	
	@Test
	public void sendPushTestOK() throws Exception {
		
		PushRequest request = new PushRequest();
		request.setBancaId("blabla");
		request.setBt("bt");
		
		PushResponse iResp = new PushResponse();
		iResp.setSended(true);
		iResp.setMsg("Push inviata");
		
		when(pushCLient.sendPush(any(PushRequest.class))).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/push/send")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		PushResponse response = mapper.readValue(resp, PushResponse.class);
		
		assertThat(response.getSended()).isTrue();
	}
	
	@Test
	public void acceptPushTestOK() throws Exception {
		
		PushRequest request = new PushRequest();
		request.setBancaId("blabla");
		request.setBt("bt");
		
		PushResponse iResp = new PushResponse();
		iResp.setSended(true);
		iResp.setAcepted(true);
		iResp.setMsg("Push accetata");
		
		when(pushCLient.acceptPush(any(PushRequest.class))).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/push/confirm")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		PushResponse response = mapper.readValue(resp, PushResponse.class);
		
		assertThat(response.getAcepted()).isTrue();
		
	}
	
	@Test
	public void getPushStatus() throws Exception {
		
		PushRequest request = new PushRequest();
		request.setBancaId("blabla");
		request.setBt("bt");
		
		PushResponse iResp = new PushResponse();
		iResp.setSended(true);
		iResp.setAcepted(true);
		iResp.setStatus("daje");
		iResp.setMsg("Push accetata");
		
		when(pushCLient.getStatusPush(any(PushRequest.class))).thenReturn(iResp);
		
		String resp = mvc.perform(post("/app/push/get")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		PushResponse response = mapper.readValue(resp, PushResponse.class);
		
		assertThat(response.getAcepted()).isTrue();
		assertThat(response.getStatus()).isEqualTo("daje");
		
	}
}

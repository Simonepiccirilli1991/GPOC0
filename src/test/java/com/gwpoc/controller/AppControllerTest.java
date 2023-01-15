package com.gwpoc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.model.response.UtenteResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

	@MockBean
	IwdbClient iwdbClient;
	@MockBean
	CachClient cachClient;
	@Autowired
	MockMvc mvc;

	ObjectMapper mapper = new ObjectMapper();


	@Test
	public void registraUtenteTestOK() throws Exception {

		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");
		request.setUsername("username");

		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setIsError(false);
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");

		when(iwdbClient.registraUt(any())).thenReturn(iResp);

		String resp = mvc.perform(post("/app/register")
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
		request.setUsername("username");

		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setIsError(false);
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");

		SessionResponse sessResp = new SessionResponse();
		sessResp.setBt("bt");
		sessResp.setScope("l2");
		sessResp.setValid(true);


		when(cachClient.getSession(any())).thenReturn(Optional.of(sessResp));

		when(iwdbClient.updateUtente(any())).thenReturn(iResp);

		String resp = mvc.perform(post("/app/update")
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
		iResp.setIsError(false);
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");
		iResp.setId(1);

		when(iwdbClient.getUtente(any())).thenReturn(iResp);

		String resp = mvc.perform(post("/app/get")
				.contentType("application/json")
				.content(mapper.writeValueAsString("bt")))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		UtenteResponse response = mapper.readValue(resp, UtenteResponse.class);

		assertThat(response.getUtente().getCf()).isEqualTo("cf");
		assertThat(response.getUtente().getId()).isEqualTo(1);
	}
	
	@Test
	public void getSessionTestKO() throws Exception {
		
		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");
		request.setUsername("username");
		
		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setIsError(false);
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");
		
		SessionResponse sessResp = new SessionResponse();
		sessResp.setBt("bt");
		sessResp.setScope("l1");
		sessResp.setValid(true);
		
		
		when(cachClient.getSession(any())).thenReturn(Optional.of(sessResp));
		
		when(iwdbClient.updateUtente(any())).thenReturn(iResp);
		
		mvc.perform(post("/app/update")
				.contentType("application/json")
				.content(mapper.writeValueAsString("bt")))
				.andExpect(status().isBadRequest());
		 


	}
}

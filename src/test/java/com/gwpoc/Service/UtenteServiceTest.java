package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.client.AnscClient;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.fragment.model.Utente;
import com.gwpoc.model.request.UtenteRequest;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.model.response.UtenteResponse;
import com.gwpoc.service.UtenteService;

@SpringBootTest
public class UtenteServiceTest {

	@Autowired
	UtenteService utenteService;
	@MockBean
	IwdbClient iwdbClient;
	@MockBean
	CachClient cachClient;
	@MockBean
	AnscClient anscClient;
	
	@Test
	public void registraUtenteTestOK() {
		
		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");
		
		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCodiceEsito("00");
		
		when(iwdbClient.registraUt(any())).thenReturn(iResp);
		
		doNothing().when(anscClient).insertAnagrafica(any());
		
		UtenteResponse response = utenteService.registra(request);
		
		assertThat(response.getBt()).isEqualTo("bt");
		assertThat(response.getRegisteredUpdated()).isEqualTo(true);
	}
	
	@Test
	public void updateUtenteTestOK() {
		
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
		
		UtenteResponse response = utenteService.update(request);
		
		assertThat(response.getMsg()).isEqualTo("Dati aggiornati con successo");
		assertThat(response.getRegisteredUpdated()).isEqualTo(true);
		
	}
	
	@Test
	public void getUtenteTestOK() {
		
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
		
		UtenteResponse response = utenteService.getUtente("bt");
		
		assertThat(response.getUtente().getBt()).isEqualTo("bt");
		assertThat(response.getUtente().getId()).isEqualTo(1);
	}
	
	
	
}

package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
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
	
	@Test
	public void registraUtenteTestOK() {
		
		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");
		request.setUsername("username");
		
		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");
		
		when(iwdbClient.registraUt(any())).thenReturn(iResp);
		
		UtenteResponse response = utenteService.registra(request);
		
		assertThat(response.getBt()).isEqualTo("bt");
		assertThat(response.getRegisteredUpdated()).isEqualTo(true);
	}
	
	@Test
	public void updateUtenteTestOK() {
		
		UtenteRequest request = new UtenteRequest();
		request.setCf("cf");
		request.setChannel("web");
		request.setUsername("username");
		
		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");
		
		SessionResponse sessResp = new SessionResponse();
		sessResp.setBt("bt");
		sessResp.setScope("l2");
		sessResp.setValid(true);
		
		
		when(cachClient.getSession(any())).thenReturn(Optional.of(sessResp));
		
		when(iwdbClient.updateUtente(any())).thenReturn(iResp);
		
		UtenteResponse response = utenteService.update(request);
		
		assertThat(response.getMsg()).isEqualTo("Dati aggiornati con successo");
		assertThat(response.getRegisteredUpdated()).isEqualTo(true);
		
	}
	
	@Test
	public void getUtenteTestOK() {
		
		UtenteIwResponse iResp = new UtenteIwResponse();
		iResp.setBt("bt");
		iResp.setCf("cf");
		iResp.setCodiceEsito("00");
		iResp.setId(1);
		
		when(iwdbClient.getUtente(any())).thenReturn(iResp);
		
		UtenteResponse response = utenteService.getUtente("bt");
		
		assertThat(response.getUtente().getCf()).isEqualTo("cf");
		assertThat(response.getUtente().getId()).isEqualTo(1);
	}
	
	
	
}

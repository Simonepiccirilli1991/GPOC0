package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.client.IwdbClient;
import com.gwpoc.fragment.iwdb.OrdiniIwResponse;
import com.gwpoc.fragment.model.Ordini;
import com.gwpoc.model.request.OrdiniRequest;
import com.gwpoc.service.OrdiniService;

@SpringBootTest
public class OrderServiceTest {

	@Autowired
	OrdiniService orderService;
	@MockBean
	IwdbClient client;
	
	
	@Test
	public void createOrderTestOK() {
		
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
		
		when(client.creaOrdine(any())).thenReturn(iResp);
		
		Ordini response = orderService.creaOrdine(request);
		
		assertThat(response.getBtAcquirente()).isEqualTo("asasd");
	}
	
	@Test
	public void getOrderTestOK() {
		
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
		
		when(client.getOrder(any())).thenReturn(iResp);
		
		Ordini response = orderService.getOrdine(request);
		
		assertThat(response.getBtAcquirente()).isEqualTo("asasd");
	}
}

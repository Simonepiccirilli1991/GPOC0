package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.client.CachClient;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.SessionService;

@SpringBootTest
public class SessionServiceTest {

	@Autowired
	SessionService sessService;
	@MockBean
	CachClient cache;
	
	@Test
	public void insertCacheTestOK() {
		
		SessionRequest request = new SessionRequest();
		request.setBt("bt1");
		request.setChannel("web");
		request.setScope("l1");
		
		SessionResponse iResp = new SessionResponse();
		iResp.setInsert(true);
		
		when(cache.createSession(any())).thenReturn(iResp);
		
		SessionResponse response = sessService.createSession(request);
		
		assertThat(response.getInsert()).isTrue();
	}
	
	@Test
	public void getCacheTestOK() {
		
		SessionRequest request = new SessionRequest();
		request.setBt("bt1");
		request.setChannel("web");
		request.setScope("l1");
		
		SessionResponse iResp = new SessionResponse();
		iResp.setInsert(true);
		iResp.setBt("bt1");
		iResp.setScope("l1");
		iResp.setValid(true);
		
		when(cache.getSession(any())).thenReturn(iResp);
		
		SessionResponse response = sessService.getSession(request);
		
		assertThat(response.getScope()).isEqualTo("l1");
	}
}

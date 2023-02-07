package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.client.OtpvPushClient;
import com.gwpoc.model.request.PushRequest;
import com.gwpoc.model.response.PushResponse;
import com.gwpoc.service.push.PushService;

@SpringBootTest
public class PushServiceTest {

	@Autowired
	PushService pushService;
	@MockBean
	OtpvPushClient pushCLient;
	
	@Test
	public void sendPushTestOK() {
		
		PushRequest request = new PushRequest();
		request.setBancaId("blabla");
		request.setBt("bt");
		
		PushResponse iResp = new PushResponse();
		iResp.setSended(true);
		iResp.setMsg("Push inviata");
		
		when(pushCLient.sendPush(any(PushRequest.class))).thenReturn(iResp);
		
		PushResponse response = pushService.sendPush(request.getBt());
		
		assertThat(response.getSended()).isTrue();
	}
	
	@Test
	public void acceptPushTestOK() {
		
		PushRequest request = new PushRequest();
		request.setBancaId("blabla");
		request.setBt("bt");
		
		PushResponse iResp = new PushResponse();
		iResp.setSended(true);
		iResp.setAcepted(true);
		iResp.setMsg("Push accetata");
		
		when(pushCLient.acceptPush(any(PushRequest.class))).thenReturn(iResp);
		
		PushResponse response = pushService.acceptPush(request.getBt());
		
		assertThat(response.getAcepted()).isTrue();
		
	}
	
	@Test
	public void getPushStatus() {
		
		PushRequest request = new PushRequest();
		request.setBancaId("blabla");
		request.setBt("bt");
		
		PushResponse iResp = new PushResponse();
		iResp.setSended(true);
		iResp.setAcepted(true);
		iResp.setStatus("daje");
		iResp.setMsg("Push accetata");
		
		when(pushCLient.getStatusPush(any(PushRequest.class))).thenReturn(iResp);
		
		PushResponse response = pushService.getStatusPush(request);
		
		assertThat(response.getAcepted()).isTrue();
		assertThat(response.getStatus()).isEqualTo("daje");
		
	}
}

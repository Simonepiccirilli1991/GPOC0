package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.Util.ActionEnum;
import com.gwpoc.client.AnscClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.fragment.iwdb.StatusIwResponse;
import com.gwpoc.model.response.AnagraficaResponse;
import com.gwpoc.model.response.StatusResponse;
import com.gwpoc.service.StatusService;

@SpringBootTest
public class StatusServiceTest {

	@Autowired
	StatusService statusService;
	@MockBean
	IwdbClient iwdb;
	@MockBean
	AnscClient ansc;
	
	@Test
	public void statusServiceTestOK() {
		
		AnagraficaResponse anag = new AnagraficaResponse();
		anag.setMailCertificata(true);
		
		StatusIwResponse statusResp = new StatusIwResponse();
		statusResp.setMsg("daje");
		statusResp.setStatus("reguster_ac");
		
		when(iwdb.getstatus(any())).thenReturn(statusResp);
		
		when(ansc.getAnagrafica(any())).thenReturn(anag);
		
		StatusResponse response = statusService.getStatus("ciao");
		
		assertThat(response.getMsg()).isEqualTo("daje");
		assertThat(response.getAction()).isEqualTo(ActionEnum.REGISTERACCOUNT);
		
	}
}

package com.gwpoc.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.fragment.model.Account;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.response.AccountResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.AccountService;

@SpringBootTest
public class AccountServiceTest {
	
	@Autowired
	AccountService accService;
	@MockBean
	IwdbClient client;
	@MockBean
	CachClient cachClient;
	
	
	@Test
	public void insertAccTestOK() {
		
		AccountRequest request = new AccountRequest();
		request.setBt("bt");
		request.setDebito(0.00);
		request.setImporto(100.00);
		request.setTipoAccount("Debito");
		
		AccountIwResponse iResp = new AccountIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setAccount(new Account());
		
		when(client.insertAccount(any())).thenReturn(iResp);
		
		AccountResponse response = accService.insertAccount(request);
		
		assertThat(response.isDone()).isTrue();
	}
	
	@Test
	public void getAccountTestOK() {
		
		Account acc = new Account();
		acc.setCodiceconto("1231231");
		acc.setDebito(0.00);
		acc.setId(1);
		acc.setSaldoattuale(100.00);
		acc.setTipoConto("Debit");
		
		AccountIwResponse iResp = new AccountIwResponse();
		iResp.setCodiceEsito("00");
		iResp.setAccount(acc);
		
		when(client.getAcc(any())).thenReturn(iResp);
		
		AccountResponse response = accService.getAccount("");
		
		assertThat(response.isDone()).isTrue();
		assertThat(response.getAccount().getCodiceconto()).isEqualTo("1231231");
	}
	
	@Test
	public void updateAccountTestOK() {
		
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
		
		when(cachClient.getSession(any())).thenReturn(Optional.of(sessResp));
		
		when(client.updateAcc(any())).thenReturn(iResp);
		
		AccountResponse response = accService.updateAccount(request);
		
		assertThat(response.isDone()).isTrue();
		assertThat(response.getAccount().getCodiceconto()).isEqualTo("1231231");
	}
	
}

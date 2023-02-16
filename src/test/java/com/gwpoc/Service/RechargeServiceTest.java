package com.gwpoc.Service;

import com.gwpoc.client.AgtwClient;
import com.gwpoc.client.CachClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.fragment.iwdb.AccountIwResponse;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.response.AccountResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.account.RechargeAccService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RechargeServiceTest {

    @Autowired
    RechargeAccService rechargeService;
    @MockBean
    AgtwClient agtwClient;
    @MockBean
    CachClient cachClient;
    @MockBean
    IwdbClient iwdbClient;


    @Test
    public void rechargeTestOK(){

        AccountRequest request = new AccountRequest();
        request.setBt("bt");
        request.setImporto(100.00);
        request.setCodiceConto("codiceConto");

        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setScope("l2");

        AccountIwResponse accountIwResponse = new AccountIwResponse();
        accountIwResponse.setCodiceEsito("OK");

        when(cachClient.getSession(any())).thenReturn(sessionResponse);

        doNothing().when(agtwClient).validateAuthBank(any());

        when(iwdbClient.rechargeAcc(any())).thenReturn(accountIwResponse);

        AccountResponse response = rechargeService.rechargeAccount(request, null);

        Assertions.assertThat(response.isDone()).isTrue();

    }
}

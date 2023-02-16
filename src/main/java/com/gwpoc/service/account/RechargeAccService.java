package com.gwpoc.service.account;

import com.gwpoc.Util.CommonUtil;
import com.gwpoc.client.AgtwClient;
import com.gwpoc.client.IwdbClient;
import com.gwpoc.error.AppException;
import com.gwpoc.model.request.AccountRequest;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.AccountResponse;
import com.gwpoc.model.response.SessionResponse;
import com.gwpoc.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RechargeAccService {

    @Autowired
    SessionService sessionServ;
    @Autowired
    AgtwClient agtwClient;
    @Autowired
    IwdbClient iwdbClient;
    public AccountResponse rechargeAccount(AccountRequest request, HttpHeaders headers){
        AccountResponse response = new AccountResponse();

        // prima di tutto controllo che ci sia sessione in L2
        SessionRequest sessR = new SessionRequest();
        sessR.setBt(request.getBt());
        SessionResponse sessResp = sessionServ.getSession(sessR);
        if(ObjectUtils.isEmpty(sessResp) || !sessResp.getScope().equals("l2")   ) {
          throw new AppException("TBD");
        }
        // poi faccio validare il jwt per operazione bancaria se non e valido stoppo , altrimenti continuo
        agtwClient.validateAuthBank(headers);
        // poi faccio la chiamata al servizio di recharge
        iwdbClient.rechargeAcc(request);

        response.setDone(true);
        return response;
    }
}

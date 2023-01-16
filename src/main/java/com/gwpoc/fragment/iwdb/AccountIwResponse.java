package com.gwpoc.fragment.iwdb;

import com.gwpoc.fragment.model.Account;
import com.gwpoc.fragment.model.IwdbBaseResponse;

public class AccountIwResponse extends IwdbBaseResponse{

	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}

package com.gwpoc.model.response;

import com.gwpoc.fragment.model.Account;

public class AccountResponse {

	private boolean done;
	private Account account;
	
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}

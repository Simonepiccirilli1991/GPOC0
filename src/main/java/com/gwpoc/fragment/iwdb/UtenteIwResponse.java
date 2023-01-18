package com.gwpoc.fragment.iwdb;

import com.gwpoc.fragment.model.Account;
import com.gwpoc.fragment.model.IwdbBaseResponse;
import com.gwpoc.fragment.model.Utente;

public class UtenteIwResponse extends IwdbBaseResponse {

	private String bt;
	private String username;
	private Account account;
	private Utente utente;

	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	
}

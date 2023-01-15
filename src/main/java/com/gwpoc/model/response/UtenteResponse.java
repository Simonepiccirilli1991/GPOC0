package com.gwpoc.model.response;

import com.gwpoc.fragment.model.Utente;

public class UtenteResponse {

	private String bt;
	private boolean registeredUpdated;
	private String msg;
	private Utente utente;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Boolean getRegisteredUpdated() {
		return registeredUpdated;
	}
	public void setRegisteredUpdated(Boolean registeredUpdated) {
		this.registeredUpdated = registeredUpdated;
	}
	
	
}

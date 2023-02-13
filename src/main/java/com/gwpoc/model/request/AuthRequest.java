package com.gwpoc.model.request;

import com.gwpoc.model.action.BaseActionRequest;

public class AuthRequest {

	private String bt;
	private String pin;
	private String auth;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	@Override
	public String toString() {
		return "AuthRequest [bt=" + bt + ", pin=" + pin + ", auth=" + auth + "]";
	}
	
	
	
}

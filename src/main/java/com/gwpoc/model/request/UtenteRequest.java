package com.gwpoc.model.request;

import org.springframework.lang.NonNull;

public class UtenteRequest {

	@NonNull
	private String username;
	@NonNull
	private String cf;
	@NonNull
	private String channel;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}

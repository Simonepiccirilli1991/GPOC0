package com.gwpoc.model.action;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gwpoc.Util.ActionEnum;

public class BaseActionResponse {
	
	private ActionEnum action;
	@JsonIgnore 
	private HttpHeaders httpHeaders;
	
	public void addHttpHeader(String headerName, String headerValue) {
		if(this.httpHeaders == null) httpHeaders = new HttpHeaders();
		this.httpHeaders.add(headerName, headerValue);
	}
	
	public ActionEnum getAction() {
		return action;
	}

	public void setAction(ActionEnum action) {
		this.action = action;
	}

	
}

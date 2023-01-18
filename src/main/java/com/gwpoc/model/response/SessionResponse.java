package com.gwpoc.model.response;

import com.gwpoc.fragment.model.BaseCacheResponse;

public class SessionResponse extends BaseCacheResponse{

	private String bt;
	private String scope;
	private Boolean valid;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	
}

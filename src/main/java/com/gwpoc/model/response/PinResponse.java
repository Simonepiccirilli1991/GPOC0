package com.gwpoc.model.response;

import com.gwpoc.model.action.BaseActionResponse;

public class PinResponse extends BaseActionResponse{

	private String email;
	private String trxId;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	
	
}

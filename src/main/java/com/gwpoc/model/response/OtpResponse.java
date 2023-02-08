package com.gwpoc.model.response;

import com.gwpoc.model.action.BaseActionResponse;

public class OtpResponse extends BaseActionResponse{

	private String trxId;
	private String msg;
	
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "OtpResponse [trxId=" + trxId + ", msg=" + msg + "]";
	}
	
	
}

package com.gwpoc.model.response;

public class GenerateOtpResponse {

	private Boolean otpSend;
	private String trxId;
	private String msg;
	
	public Boolean getOtpSend() {
		return otpSend;
	}
	public void setOtpSend(Boolean otpSend) {
		this.otpSend = otpSend;
	}
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
		return "GenerateOtpResponse [otpSend=" + otpSend + ", trxId=" + trxId + ", msg=" + msg + "]";
	}
	
	
}

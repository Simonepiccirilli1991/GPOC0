package com.gwpoc.model.request;

import com.gwpoc.model.action.BaseActionRequest;

public class OtpRequest extends BaseActionRequest{

	private String bt;
	private String email;
	private String trxId;
	private String otp;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
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
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	@Override
	public String toString() {
		return "OtpRequest [bt=" + bt + ", email=" + email + ", trxId=" + trxId + ", otp=" + otp + "]";
	}
	
	
}

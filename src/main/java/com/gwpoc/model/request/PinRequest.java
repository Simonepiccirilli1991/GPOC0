package com.gwpoc.model.request;

import com.gwpoc.model.action.BaseActionRequest;

public class PinRequest extends BaseActionRequest{

	private String bt;
	private String otp;
	private String trxId;
	private String pin;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	@Override
	public String toString() {
		return "PinRequest [bt=" + bt + ", otp=" + otp + ", trxId=" + trxId + ", pin=" + pin + "]";
	}
	
	
}

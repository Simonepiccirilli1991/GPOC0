package com.gwpoc.fragment.model;

public class IwdbBaseResponse {

	
	private String msg;
	private String codiceEsito;
	private boolean isError;
	private String errDsc;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCodiceEsito() {
		return codiceEsito;
	}
	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}
	public String getErrDsc() {
		return errDsc;
	}
	public void setErrDsc(String errDsc) {
		this.errDsc = errDsc;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	
	
	
	
}

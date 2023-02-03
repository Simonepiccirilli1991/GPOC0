package com.gwpoc.fragment.iwdb;

import com.gwpoc.fragment.model.IwdbBaseResponse;

public class StatusIwResponse extends IwdbBaseResponse{

	private String msg;
	private String status;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

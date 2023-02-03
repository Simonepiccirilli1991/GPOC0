package com.gwpoc.model.response;

import com.gwpoc.Util.ActionEnum;

public class StatusResponse {

	private String status;
	private String msg;
	private ActionEnum action;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ActionEnum getAction() {
		return action;
	}
	public void setAction(ActionEnum action) {
		this.action = action;
	}
	
	
}

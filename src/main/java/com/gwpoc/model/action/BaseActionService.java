package com.gwpoc.model.action;

import org.springframework.http.HttpHeaders;


public abstract class BaseActionService <I extends BaseActionRequest, O extends BaseActionResponse>{

	protected final String className;

	protected BaseActionService(){ this.className = null; }
	protected BaseActionService(String className) { this.className = className; }

	public O lunchService(I iRequest, HttpHeaders httpHeaders)  {
		O oResponse = lunchService_(iRequest,httpHeaders);
		return oResponse;
	}

	public abstract O lunchService_(I iRequest, HttpHeaders httpHeaders);
	
}

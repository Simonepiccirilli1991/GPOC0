package com.gwpoc.model.action;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;



public class BaseActionCommand <I extends BaseActionRequest, O extends BaseActionResponse> {

	@Autowired
	private BeanFactory beanFactory;
	protected I iRequest;
	protected HttpHeaders httpHeaders;
	
	public BaseActionCommand(I iRequest,HttpHeaders httpHeaders) {
		this.iRequest = iRequest;
		this.httpHeaders = httpHeaders;
		
	}
	
	protected boolean canExecute() {
		return this.iRequest != null;
	}

	public final <T extends BaseActionService<I, O>> O getResponse(Class<T> serviceClass){

		return beanFactory.getBean(serviceClass).lunchService(iRequest, httpHeaders);
	}
}

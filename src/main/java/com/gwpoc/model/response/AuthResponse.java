package com.gwpoc.model.response;

import com.gwpoc.model.action.BaseActionResponse;

public class AuthResponse {

	private Boolean generated;

	public Boolean getGenerated() {
		return generated;
	}

	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}

	@Override
	public String toString() {
		return "AuthResponse [generated=" + generated + "]";
	}
	
	
}

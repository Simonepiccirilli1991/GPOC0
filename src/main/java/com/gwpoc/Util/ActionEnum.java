package com.gwpoc.Util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionEnum {
	
	GENERATEAUTH("generate_auth"),
	CHECKAUTH("check_auth"),
	CHECKPIN("checkpin"),
	CHANGEPIN("changepin"),
	CONSENT("consent"),
	SENDOTP("sendotp"),
	SENDOTPCERTIFY("sendotpcertify"),
	CHECKOTP("checkotp"),
	CERTIFYMAIL("certifymail"),
	CHANGEMAIL("changemail"),
	REGISTERACCOUNT("registeraccount");
	

	private final String value;

	ActionEnum(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@JsonValue
	public String value() {
		return value;
	}
	
}

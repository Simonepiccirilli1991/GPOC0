package com.gwpoc.Util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionEnum {

	CHECKPIN("checkpin"),
	CHANGEPIN("changepin"),
	CONSENT("consent"),
	SENDOTP("sendotp"),
	CHECKOTP("checkotp"),
	CERTIFYMAIL("certifymail"),
	CHANGEMAIL("changemail");
	

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

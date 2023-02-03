package com.gwpoc.Util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConstEnum {
	
	//scope session lvl
	L2("l2"),
	L1("l1");
	
	
	// status iwdb
	public final static String UTENTE_NOFOUND = "register_ut";
	public final static String ACCOUNT_NOFOUND = "reguster_ac";
	public final static String REGISTRATION_FOUND = "registered";
	
	
	private final String value;

	ConstEnum(String value) {
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

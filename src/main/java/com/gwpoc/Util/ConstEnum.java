package com.gwpoc.Util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConstEnum {

	L2("l2"),
	L1("l1");

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

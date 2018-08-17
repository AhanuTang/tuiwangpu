package com.test.pojo;

public class Account {
	private String value;
	private String accountName;
	private String span;

	public Account() {
		super();
	}

	public Account(String value, String accountName, String span) {
		super();
		this.value = value;
		this.accountName = accountName;
		this.span = span;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

}

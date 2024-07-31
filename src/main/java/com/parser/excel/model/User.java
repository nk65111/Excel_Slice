package com.parser.excel.model;


public class User {
    
    public User(String name, String mobileNumber, String requirement, String email) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.requirement = requirement;
		this.email = email;
	}
	private String name;
    private String mobileNumber;
    private String requirement;
    private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User() {
		super();
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

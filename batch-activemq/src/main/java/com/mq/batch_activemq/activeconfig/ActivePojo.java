package com.mq.batch_activemq.activeconfig;

public class ActivePojo {
	private String conUrl;
	private String userName;
	private String password;
	private String model;
	private String destion;
	public String getConUrl() {
		return conUrl;
	}
	public void setConUrl(String conUrl) {
		this.conUrl = conUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDestion() {
		return destion;
	}
	public void setDestion(String destion) {
		this.destion = destion;
	}
	@Override
	public String toString() {
		return "ActivePojo [conUrl=" + conUrl + ", userName=" + userName + ", password=" + password + ", model=" + model
				+ ", destion=" + destion + "]";
	}
	
}

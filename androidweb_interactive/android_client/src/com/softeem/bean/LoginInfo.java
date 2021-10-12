package com.softeem.bean;

public class LoginInfo {

	private int code;
	private String msg;
	private boolean isSuccess;
	
	public LoginInfo() {
	}
	
	public LoginInfo(int code, String msg, boolean isSuccess) {
		super();
		this.code = code;
		this.msg = msg;
		this.isSuccess = isSuccess;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	@Override
	public String toString() {
		return "LoginInfo [code=" + code + ", msg=" + msg + ", isSuccess="
				+ isSuccess + "]";
	}
}

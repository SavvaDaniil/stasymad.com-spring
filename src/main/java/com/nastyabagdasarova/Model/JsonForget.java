package com.nastyabagdasarova.Model;

public class JsonForget {
	private String answer;
	private String error;
	private String code;
	private String hash;
	
	
	public JsonForget(String answer, String error, String code, String hash) {
		super();
		this.answer = answer;
		this.error = error;
		this.code = code;
		this.hash = hash;
	}
	
	public JsonForget() {
		super();
	}

	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}

	
}

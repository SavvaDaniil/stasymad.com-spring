package com.nastyabagdasarova.Model;

import java.util.List;

public class Json {
	private String answer;
	private String error;
	private List<Video> content;
	private List<Chat> chat;
	private String sendedname;
	private String from_who;
	
	private Demo demo;
	private int type_demo0_course1;
	
	public Json(String answer, String error){
		this.answer = answer;
		this.error = error;
	}
	
	
	public Json(String answer, String error, Demo demo, int type_demo0_course1) {
		super();
		this.answer = answer;
		this.error = error;
		this.demo = demo;
		this.type_demo0_course1 = type_demo0_course1;
	}

	public Json(String answer, String error, List<Video> content) {
		super();
		this.answer = answer;
		this.error = error;
		this.content = content;
	}

	public Json(String answer, String error, List<Chat> chat, String sendedname) {
		super();
		this.answer = answer;
		this.error = error;
		this.chat = chat;
		this.sendedname = sendedname;
	}

	public Json(){}
	
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

	public List<Video> getContent() {
		return content;
	}

	public void setContent(List<Video> content) {
		this.content = content;
	}

	public List<Chat> getChat() {
		return chat;
	}

	public void setChat(List<Chat> chat) {
		this.chat = chat;
	}

	public String getSendedname() {
		return sendedname;
	}

	public void setSendedname(String sendedname) {
		this.sendedname = sendedname;
	}

	public String getFrom_who() {
		return from_who;
	}

	public void setFrom_who(String from_who) {
		this.from_who = from_who;
	}

	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}


	public int getType_demo0_course1() {
		return type_demo0_course1;
	}


	public void setType_demo0_course1(int type_demo0_course1) {
		this.type_demo0_course1 = type_demo0_course1;
	}
	
	
}

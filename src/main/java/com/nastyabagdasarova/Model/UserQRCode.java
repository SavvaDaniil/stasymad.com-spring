package com.nastyabagdasarova.Model;

import com.nastyabagdasarova.Prototype.Copyable;

public class UserQRCode implements Copyable {

	public UserQRCode() {
		this.basePath = "qr";
	}
	
	
	
	public UserQRCode(int id_of_user, String username, String basePath, String src, String message) {
		super();
		this.id_of_user = id_of_user;
		this.username = username;
		this.basePath = basePath;
		this.src = src;
		this.message = message;
	}



	public int id_of_user;
	public String username;
	public String basePath;
	public String src;
	public String message;
	
	public int getId_of_user() {
		return id_of_user;
	}
	public void setId_of_user(int id_of_user) {
		this.id_of_user = id_of_user;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public Object copy() {
		UserQRCode copy = new UserQRCode(id_of_user, username, basePath, src, message);
		return copy;
	}
	
	
}

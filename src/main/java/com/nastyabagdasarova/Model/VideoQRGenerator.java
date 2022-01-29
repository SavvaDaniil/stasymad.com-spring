package com.nastyabagdasarova.Model;

public final class VideoQRGenerator {

	private static VideoQRGenerator instance;
	private int status;
	private User user;
	private Accessofuser accessofuser;
	/*

	0 - не запущен
	1 - генерирует VideoQR
	2 - разбивает видео на фрагмент
	
	 */
	
	private VideoQRGenerator() {
		this.status = 0;
	}
	
	public static VideoQRGenerator getInstance() {
		if(instance == null) {
			instance = new VideoQRGenerator();
		}
		
		return instance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Accessofuser getAccessofuser() {
		return accessofuser;
	}

	public void setAccessofuser(Accessofuser accessofuser) {
		this.accessofuser = accessofuser;
	}
	
	
	
	
}

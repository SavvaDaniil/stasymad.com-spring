package com.nastyabagdasarova.Interface;

public interface IMainEventListener {
	void updateToEverybody(String subject, String message);
	void updateToDeveloper(String subject, String message);
	void updateToAdmin(String subject, String message);
}

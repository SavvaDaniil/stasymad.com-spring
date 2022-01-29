package com.nastyabagdasarova.Listener;

import com.nastyabagdasarova.Component.EmailThread;
import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Interface.IMainEventListener;

public class MainEventListener implements IMainEventListener {

	@Override
	public void updateToEverybody(String subject, String message) {
		EmailThread emailThread = new EmailThread(null, FinalComponent.emailDeveloper, subject, message, null, null);
		emailThread.start();

		EmailThread emailThread2 = new EmailThread(null, FinalComponent.emailAdmin, subject, message, null, null);
		emailThread2.start();
	}

	@Override
	public void updateToDeveloper(String subject, String message) {
		EmailThread emailThread = new EmailThread(null, FinalComponent.emailDeveloper, subject, message, null, null);
		emailThread.start();
	}

	@Override
	public void updateToAdmin(String subject, String message) {
		// TODO Auto-generated method stub
		
	}

}

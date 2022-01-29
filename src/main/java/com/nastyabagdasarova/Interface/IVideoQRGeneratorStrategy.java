package com.nastyabagdasarova.Interface;

import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.User;

public interface IVideoQRGeneratorStrategy {
	String launch(User user, Accessofuser accessofuser);
	String reload(User user, Accessofuser accessofuser);
}

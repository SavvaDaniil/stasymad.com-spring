package com.nastyabagdasarova.Interface;

import com.nastyabagdasarova.Model.UserQRCode;

public interface IUserQRCodeFactory {
	UserQRCode create(int id_of_user, String username);
	UserQRCode clone(UserQRCode userQRCode);
}

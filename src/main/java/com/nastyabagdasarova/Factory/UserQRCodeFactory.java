package com.nastyabagdasarova.Factory;

import java.io.File;
import com.nastyabagdasarova.Builder.QRCodeGenerator;
import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Interface.IUserQRCodeFactory;
import com.nastyabagdasarova.Model.UserQRCode;

public class UserQRCodeFactory implements IUserQRCodeFactory {
	
	@Override
	public UserQRCode create(int id_of_user, String username) {
		UserQRCode userQRCode = new UserQRCode();
		userQRCode.setId_of_user(id_of_user);
		
		File file = new File(userQRCode.getBasePath() + "/" + id_of_user);
		if(!file.exists()) {
			file.mkdir();
		}
		file = new File(FinalComponent.dynamicVideoFolder + "/" + id_of_user);
		if(!file.exists()) {
			file.mkdir();
		}
		userQRCode.setSrc(userQRCode.getBasePath() + "/" + userQRCode.getId_of_user() + ".png");
		userQRCode.setMessage("Downloaded from the site " + FinalComponent.documentRoot + " by " + username
		+ "(id"+id_of_user+"_dfhgdr34)");
		
		file = new File(userQRCode.getSrc());
		if(!file.exists()) {
			QRCodeGenerator.generateQRCodeImage(userQRCode);
		}
		file = null;
		
		username = null;
		System.gc();
		
		return userQRCode;
	}

	@Override
	public UserQRCode clone(UserQRCode userQRCode) {
		return (UserQRCode) userQRCode.copy();
	}

}

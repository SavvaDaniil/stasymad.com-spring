package com.nastyabagdasarova.Strategy;

import java.io.File;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.nastyabagdasarova.Builder.QRCodeGenerator;
import com.nastyabagdasarova.Factory.UserQRCodeFactory;
import com.nastyabagdasarova.Interface.IVideoQRGeneratorStrategy;
import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.UserQRCode;
import com.nastyabagdasarova.Model.VideoQRGenerator;
import com.nastyabagdasarova.Service.VideoService;

public class VideoQRGeneratorStrategy implements IVideoQRGeneratorStrategy {

	@Override
	public String launch(User user, Accessofuser accessofuser) {

		VideoQRGenerator videoQRGenerator = VideoQRGenerator.getInstance();
		if(videoQRGenerator.getStatus() == 1) {
			return "videoQRGenerator в процессе generateVideoQR"
				+ " для пользователя ("+user.getId()+") "+user.getUsername()+""
				+ " для доступа "+accessofuser.getId()+" для курса "+accessofuser.getId_of_course();
		}
		if(videoQRGenerator.getStatus() == 2) {
			return "videoQRGenerator в процессе gererateVideoQRStream"
				+ " для пользователя ("+user.getId()+") "+user.getUsername()+""
				+ " для доступа "+accessofuser.getId()+" для курса "+accessofuser.getId_of_course();
		}
		
		//проверяем, на какйо стадии доступ
		videoQRGenerator.setUser(user);
		videoQRGenerator.setAccessofuser(accessofuser);
		
		//смотрим, готово ли видео по фрагментам
		
		
		//если нет, готово ли видео с QR кодом
		
		
		return null;
	}

	private String generateVideoQR(VideoQRGenerator videoQRGenerator) {
		//получаем QR-код
		UserQRCodeFactory userQRCodeFactory = new UserQRCodeFactory();
		UserQRCode userQRCode = userQRCodeFactory.create(videoQRGenerator.getUser().getId(), videoQRGenerator.getUser().getUsername());
		File userQRFile = new File(userQRCode.getSrc());
		if(!userQRFile.exists()) {
			QRCodeGenerator.generateQRCodeImage(userQRCode);
		}
		
		
		//запускаем видео на генерацию
		//VideoService videoService = new VideoService();
		//Video video = videoService.findById(videoQRGenerator.getAccessofuser().getId_of_course());
		

		return null;
	}

	private String gererateVideoQRStream(User user, Accessofuser accessofuser) {

		return null;
	}

	@Override
	public String reload(User user, Accessofuser accessofuser) {

		VideoQRGenerator videoQRGenerator = VideoQRGenerator.getInstance();
		if(videoQRGenerator.getStatus() == 1) {
			return "videoQRGenerator в процессе generateVideoQR"
				+ " для пользователя ("+user.getId()+") "+user.getUsername()+""
				+ " для доступа "+accessofuser.getId()+" для курса "+accessofuser.getId_of_course();
		}
		if(videoQRGenerator.getStatus() == 2) {
			return "videoQRGenerator в процессе gererateVideoQRStream"
				+ " для пользователя ("+user.getId()+") "+user.getUsername()+""
				+ " для доступа "+accessofuser.getId()+" для курса "+accessofuser.getId_of_course();
		}

		
		return null;
	}

}

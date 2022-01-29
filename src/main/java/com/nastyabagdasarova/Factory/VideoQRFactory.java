package com.nastyabagdasarova.Factory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Component.VideoQRThread;
import com.nastyabagdasarova.Interface.IVideoQRFactory;
import com.nastyabagdasarova.Model.UserQRCode;
import com.nastyabagdasarova.Model.VideoQR;
import com.nastyabagdasarova.Service.RandomService;
import com.nastyabagdasarova.Service.VideoQRService;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.Video;


public class VideoQRFactory implements IVideoQRFactory {

	@Override
	public VideoQR create(User user, Video video, UserQRCode userQRCode) {
		
		VideoQR videoQR = new VideoQR(user.getId(), video.getId(), userQRCode);

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		
		videoQR.setDate_of_add(ptsc.parseTimestamp(timestamp));
		timestamp = null;
		ptsc = null;
		
		RandomService randomService = new RandomService();
		videoQR.setHash(randomService.generatedRandomString(64));
		randomService = null;
		
		//launchThreadCreateQRContent(videoQR, video, userQRCode);
		
		
		File file = new File(videoQR.getBasePath() + "/" + user.getId());
		if(!file.exists()) {
			file.mkdir();
		}
		file = new File(videoQR.getBasePath() + "/" + user.getId() + "/" + video.getId() + "_" + videoQR.getHash());
		if(!file.exists()) {
			file.mkdir();
		}
		
		videoQR.setSrc(videoQR.getBasePath() + "/" + user.getId() + "/" + video.getId() + "_" + videoQR.getHash() + "/playlist.m3u8");
		
		
		file = null;
		System.gc();
		
		return videoQR;
	}
	
	public VideoQR cloneProject(VideoQR videoQR) {
		return (VideoQR) videoQR.copy();
	}
	
	
	
	public boolean checkQRContentAndlaunchThreadCreateQRContent(VideoQR videoQR, Video video, UserQRCode userQRCode) {

		if(videoQR.getStatus() == 1) {
			//значит уже выполняется, и ждем
			System.out.println("Thread уже запущен");
			return false;
		}
		
		File file = new File(video.getSrcOutput());
		if(!file.exists()) {
			System.out.println("Не найден файл видео = " + video.getSrcOutput());
			return false;
		}
		
		file = null;
		
		
		//ищем, у кого доступ равно 1, если есть, значит очередь, так что просто 1 ставим
		
		VideoQRService videoQRService = new VideoQRService();
		
		if(videoQRService.isQueueFreeForConvert()) {
			//запускаем
			//VideoQRThread videoQRThread = new VideoQRThread(videoQR, video);
			//videoQRThread.start();
		}
		videoQRService = null;
		
		return true;
	}

	
}

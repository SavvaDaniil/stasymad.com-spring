package com.nastyabagdasarova.Component;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import com.nastyabagdasarova.Model.Video;
import com.nastyabagdasarova.Model.VideoQR;

public class VideoQRThread extends Thread {

	private VideoQR videoQR;
	private Video video;
	
	public VideoQRThread(VideoQR videoQR, Video video){
		this.videoQR = videoQR;
		this.video = video;
	}

	@Override
	public void run() {
		try {
			System.out.println("Запуск Thread на создание QR Контента");
			copyVideoFileAndplaceWatermark();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void copyVideoFileAndplaceWatermark() throws IOException, InterruptedException {

		File file = new File(video.getSrc());
		if(!file.exists()) {
			file = null;
			return;
		}
		//проверяем, может файл уже готов
		file = new File(videoQR.getSrcOutput());
		if(file.exists()) {
			file = null;
			return;
		}
		
		
		//накладываем водяной знак
		file = new File(videoQR.getUserQRCode().getSrc());
		if(!file.exists()) {
			return;
		}
		
		String neededFilePath = video.getSrcOutput();
		String CopyFilePath = videoQR.getSrcOutput();
		String QRPath = videoQR.getUserQRCode().getSrc();
		String command = "ffmpeg.exe -y -i "+neededFilePath+" -i "+ QRPath
				+ " -filter_complex \"overlay=(main_w-overlay_w)-2:(main_h-overlay_h)-30\" "
				+ " -codec:v libx264 -crf 12 -preset veryslow -pix_fmt yuv420p -c:a aac -strict -2 "+CopyFilePath;
		
		System.out.println("command = " + command);
		
		/*
		Process p = Runtime.getRuntime().exec(command);
		System.out.println("Waiting for batch file ...");
		p.waitFor();
		System.out.println("Batch file done.");
		*/
		
		neededFilePath = null;
		CopyFilePath = null;
		QRPath = null;
		command = null;
		System.gc();
	}
}

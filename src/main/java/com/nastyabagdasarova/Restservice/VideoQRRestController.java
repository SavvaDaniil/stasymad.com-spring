package com.nastyabagdasarova.Restservice;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Service.UserService;

@RestController
public class VideoQRRestController {

	@Autowired
	UserService userService;

	@GetMapping("/video123/{idAndHash}/{nameOfFile}")
	@ResponseBody
	public byte[] getVideo(
			@PathVariable(value="idAndHash") final String idAndHash,
			@PathVariable(value="nameOfFile") final String nameOfFile
			) throws IOException {
		
		if(idAndHash.equals("") || nameOfFile.equals("")) {
			return null;
		}
		
		
		File file = new File("video/"+idAndHash+"/"+nameOfFile);
		if(!file.exists()) return null;
			
		
		
		InputStream in = new FileInputStream(file);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int read;
		try {
			while ((read = in.read(buffer)) != -1) {
				os.write(buffer, 0, read);
			}
		} finally {
			in.close();
		}
		file = null;
		in = null;
		buffer = null;
		System.gc();
		
		return os.toByteArray();
		
		/*
		user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		
			
		//получаем QR-код
		UserQRCodeFactory userQRCodeFactory = new UserQRCodeFactory();
		userQRCode = userQRCodeFactory.create(user.getId(), user.getUsername());
		userQRFile = new File(userQRCode.getSrc());
		if(!userQRFile.exists()) {
			QRCodeGenerator.generateQRCodeImage(userQRCode);
		}
		
		Integer dynamicVideoIndex = user.getDynamic_video_index();
		if(dynamicVideoIndex > 10) {
			dynamicVideoIndex = 0;
		}
		dynamicVideoIndex += 1;
		user.setDynamic_video_index(dynamicVideoIndex);
		userService.save(user);
		
		//запускаем перенос файла
		String neededFilePath = "video/"+idAndHash+"/"+nameOfFile;
		String CopyFilePath = FinalComponent.dynamicVideoFolder + "/" + user.getId() + "/video"+dynamicVideoIndex.toString()+".ts";
		String QRPath = userQRCode.getSrc();
		String command = "ffmpeg.exe -y -i "+neededFilePath+" -i "+ QRPath
				+ " -filter_complex \"overlay=(main_w-overlay_w)-2:(main_h-overlay_h)-2\" "
				+ " -codec:v libx264 -crf 18 -preset slow -pix_fmt yuv420p -c:a aac -strict -2 "+CopyFilePath;
		
		System.out.println("command = " + command);
		
		Process p = Runtime.getRuntime().exec(command);
		//ProcessBuilder pb = new ProcessBuilder();
		
		//pb.command(command);
		
		//Process p = pb.start();
		
		
		System.out.println("Waiting for batch file ...");

		Thread.sleep(2000);
		//p.waitFor();
		p.getErrorStream().close();
		p.getInputStream().close();
		p.getOutputStream().close();
		System.out.println("Batch file done.");
		
		//pb = null;
		p = null;
		//ffmpeg.exe -y -i video.ts -i 1.png -filter_complex "overlay=(main_w-overlay_w)-2:(main_h-overlay_h)-2"
		//-codec:v libx264 -crf 18 -preset slow -pix_fmt yuv420p -c:a aac -strict -2 video2.ts
		
		System.out.println("CopyFilePath = " + CopyFilePath);
		
		File CopyFilePathFileContent = new File(CopyFilePath);
		InputStream in = new FileInputStream(CopyFilePathFileContent);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int read;
		try {
			while ((read = in.read(buffer)) != -1) {
				os.write(buffer, 0, read);
			}
		} finally {
			in.close();
		}

		neededFilePath = null;
		CopyFilePath = null;
		QRPath = null;
		command = null;
		CopyFilePathFileContent = null;
		file = null;
		userQRCodeFactory = null;
		in = null;
		buffer = null;
		System.gc();
		
		return os.toByteArray();
		*/
	
		
		
		
		
		
	}
	
	
}

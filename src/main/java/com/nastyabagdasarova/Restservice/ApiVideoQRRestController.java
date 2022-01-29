package com.nastyabagdasarova.Restservice;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Factory.UserQRCodeFactory;
import com.nastyabagdasarova.Factory.VideoQRFactory;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.UserQRCode;
import com.nastyabagdasarova.Model.Video;
import com.nastyabagdasarova.Model.VideoQR;
import com.nastyabagdasarova.Service.UserService;
import com.nastyabagdasarova.Service.VideoQRService;
import com.nastyabagdasarova.Service.VideoService;

@RestController
public class ApiVideoQRRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private VideoQRService videoQRService;
	
	
	@GetMapping("/api/videoqr")
	@ResponseBody
	public String getvideo(
			@RequestParam(defaultValue = "0") int id_of_user,
			@RequestParam(defaultValue = "0") int id_of_video
			) {
		if(id_of_user == 0 || id_of_video == 0) {
			return "no_data";
		}
		
		User currentUser = userService.findById(id_of_user);
		if(currentUser == null) {
			return "user_not_found";
		}
		Video video = videoService.findById(id_of_video);
		if(video == null) {
			currentUser = null;
			System.gc();
			return "video_not_found";
		}
		
		video.setSrc(video.getBasePath() + "/" + video.getId() + "_" + video.getHash() + "/playlist.m3u8");
		video.setSrcOutput(video.getBasePath() + "/" + video.getId() + "_" + video.getHash() + "/output.mp4");
		

		VideoQR videoQR;
		VideoQRFactory videoQRFactory = new VideoQRFactory();
		UserQRCodeFactory userQRCodeFactory = new UserQRCodeFactory();
		UserQRCode userQRCode;
		
		userQRCode = userQRCodeFactory.create(currentUser.getId(), currentUser.getUsername());

		videoQR = videoQRService.findByIdOfContentOrReturnNull(currentUser.getId(), video.getId());
		if(videoQR == null) {
			videoQR = videoQRFactory.create(currentUser, video, userQRCode);
		} else {
			videoQR.setUserQRCode(userQRCode);
		}

		videoQR.updateSrc();
		videoQR.updateSrcOutput();
		
		String neededFilePath = video.getSrcOutput();
		String CopyFilePath = videoQR.getSrcOutput();
		String QRPath = videoQR.getUserQRCode().getSrc();
		
		String command = "ffmpeg.exe -y -i "+neededFilePath+" -i "+ QRPath
				+ " -filter_complex \"overlay=(main_w-overlay_w)-2:(main_h-overlay_h)-50\" "
				+ " -codec:v libx264 -crf 12 -preset veryslow -pix_fmt yuv420p -c:a aac -strict -2 "+CopyFilePath;
		
		currentUser = null;
		video = null;
		videoQR = null;
		videoQRFactory = null;
		userQRCodeFactory = null;
		neededFilePath = null;
		CopyFilePath = null;
		QRPath = null;
		System.gc();
		
		
		
		return command;
	}
	
	
	@GetMapping("/api/videoqr_ts")
	@ResponseBody
	public String videoSplit(
			@RequestParam(defaultValue = "0") int id_of_user,
			@RequestParam(defaultValue = "0") int id_of_video
			) {
		if(id_of_user == 0 || id_of_video == 0) {
			return "no_data";
		}
		
		User currentUser = userService.findById(id_of_user);
		if(currentUser == null) {
			return "user_not_found";
		}
		Video video = videoService.findById(id_of_video);
		if(video == null) {
			currentUser = null;
			System.gc();
			return "video_not_found";
		}
		
		video.setSrc(video.getBasePath() + "/" + video.getId() + "_" + video.getHash() + "/playlist.m3u8");
		video.setSrcOutput(video.getBasePath() + "/" + video.getId() + "_" + video.getHash() + "/output.mp4");
		

		VideoQR videoQR;
		VideoQRFactory videoQRFactory = new VideoQRFactory();
		UserQRCodeFactory userQRCodeFactory = new UserQRCodeFactory();
		UserQRCode userQRCode;
		
		userQRCode = userQRCodeFactory.create(currentUser.getId(), currentUser.getUsername());

		videoQR = videoQRService.findByIdOfContentOrReturnNull(currentUser.getId(), video.getId());
		if(videoQR == null) {
			videoQR = videoQRFactory.create(currentUser, video, userQRCode);
		} else {
			videoQR.setUserQRCode(userQRCode);
		}

		videoQR.updateSrc();
		videoQR.updateSrcOutput();
		videoQR.updateSrcTSBase();
		
		File file = new File(video.getSrcOutput());
		if(!file.exists()) {
			currentUser = null;
			video = null;
			videoQR = null;
			videoQRFactory = null;
			userQRCodeFactory = null;
			file = null;
			System.gc();
			return "output_not_found";
		}
		
		
		
		
		String command = "ffmpeg.exe -i "+videoQR.getSrcOutput()+" -codec copy -c:a aac -map 0 "
				+ " -f segment -segment_list " + videoQR.getSrc()
				+ " -segment_list_flags +live -segment_time 4 "+videoQR.getSrcTSBase()+"/out%03d.ts";
		
		
		
		currentUser = null;
		video = null;
		videoQR = null;
		videoQRFactory = null;
		userQRCodeFactory = null;
		file = null;
		System.gc();
		
		
		
		return command;
	}
}

package com.nastyabagdasarova.Restservice;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Factory.UserQRCodeFactory;
import com.nastyabagdasarova.Factory.VideoFactory;
import com.nastyabagdasarova.Factory.VideoQRFactory;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.Json;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.UserQRCode;
import com.nastyabagdasarova.Model.Video;
import com.nastyabagdasarova.Model.VideoQR;
import com.nastyabagdasarova.Model.Videoplay;
import com.nastyabagdasarova.Service.AccessofuserService;
import com.nastyabagdasarova.Service.PreferenceService;
import com.nastyabagdasarova.Service.ReLoginService;
import com.nastyabagdasarova.Service.UserService;
import com.nastyabagdasarova.Service.VideoQRService;
import com.nastyabagdasarova.Service.VideoService;
import com.nastyabagdasarova.Service.VideoplayService;

@RestController
public class AccountRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private VideoQRService videoQRService;
	@Autowired
	private PreferenceService preferenceService;
	@Autowired
	private AccessofuserService accessofuserService;
	@Autowired
	private VideoplayService videoplayService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
	@Autowired 
	private PasswordEncoder passwordEncoder;
    
	@PostMapping("/rest/account/save")
	@ResponseBody
	public Json save(
			@RequestParam(defaultValue = "") String username,
			@RequestParam(defaultValue = "") String fio,
			@RequestParam(defaultValue = "") String new_password,
			@RequestParam(defaultValue = "") String new_password_confirm,
			@RequestParam(defaultValue = "") String password
			) {
		Json answer = new Json();
		
		if(username.equals("") || fio.equals("")) {
			answer = new Json("error","no_data");
			return answer;
		}
		
		
		
		String currentUserName = authenticationFacade.getAuthentication().getName();
		User currentUser = userService.findByUsername(currentUserName);
		boolean isNeedRelogin = false;
		
		if(currentUser.getUsername() != username) {
			if(!userService.isAlreadyExistUsername(currentUser.getId(), username)) {
				currentUser.setUsername(username);
				isNeedRelogin = true;
			} else {
				answer = new Json("error","username_already_exist");
				return answer;
			}
		}
		if(currentUser.getFio() != fio) {
			currentUser.setFio(fio);
		}
		
		
		if(!new_password.equals("")){
			if(!new_password_confirm.equals(new_password)){
				answer = new Json("error","wrong_new_password");
				return answer;
		    } else if(!passwordEncoder.matches(password, currentUser.getPassword())){
				answer = new Json("error","wrong_password");
				return answer;
		    } else {
		    	currentUser.setPassword(passwordEncoder.encode(new_password));
		    	isNeedRelogin = true;
		    }
		}
		userService.save(currentUser);
		if(isNeedRelogin) {
			ReLoginService reLogin = new ReLoginService();
			reLogin.init(currentUser.getUsername(), currentUser.getPassword());
			reLogin = null;
		}
		
		currentUserName = null;
		currentUser = null;
		System.gc();
		
		answer = new Json("success","");
		return answer;
	}
	

	@GetMapping("/account/getvideo")
	@ResponseBody
	public Json getvideo(
			@RequestParam(defaultValue = "0") int id_of_content,
			@RequestParam(defaultValue = "0") int number
			) {
		Json answer = new Json();
		if(id_of_content == 0 || number == 0) {
			answer = new Json("error","no_data", null);
			return answer;
		}
		
		String currentUserName = authenticationFacade.getAuthentication().getName();
		User currentUser = userService.findByUsername(currentUserName);
		

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();


		List<Video> listVideo = videoService.findAllByIdOfContent(id_of_content, currentUser);
		if(listVideo.isEmpty()) {
			answer = new Json("error","server_error", null);
			return answer;
		}
		
		//для доступа по qr контенту
		VideoQR videoQR;
		VideoQRFactory videoQRFactory = new VideoQRFactory();
		//VideoFactory videoFactory = new VideoFactory();
		UserQRCodeFactory userQRCodeFactory = new UserQRCodeFactory();
		UserQRCode userQRCode;
		
		if(preferenceService.isUseQRContent().equals("1")) {
			
			for(Video video : listVideo) {
	
				userQRCode = userQRCodeFactory.create(currentUser.getId(), currentUser.getUsername());
				
				//ищем videoqr в базе, если нет, создаем
				videoQR = videoQRService.findByIdOfContentOrReturnNull(currentUser.getId(), video.getId());
				if(videoQR == null) {
					videoQR = videoQRFactory.create(currentUser, video, userQRCode);
				} else {
					videoQR.setUserQRCode(userQRCode);
				}
	
	
				videoQR.updateSrc();
				videoQR.updateSrcOutput();
				videoQR.updateSrcTSBase();
				
				//проверяем на доступ к QR-видео
				File file = new File(videoQR.getSrc());
				if(!file.exists() || videoQR.getStatus() == 0) {
				
					//значит не создано, запускаем Thread на запуск
					//videoQRFactory.checkQRContentAndlaunchThreadCreateQRContent(
					//		videoQRFactory.cloneProject(videoQR),
					//		videoFactory.clone(video),
					//		userQRCodeFactory.clone(userQRCode));
					
					//video.setStatus(1);
					
				}
				file = null;
				videoQRService.save(videoQR);
				
				
				video.setSrc(videoQR.getSrc());
				video.setStatusQRContent(videoQR.getStatus());
				if(videoQR.getStatus() == 2) {
					video.setSrc(videoQR.getSrc());
				} else {
					video.setSrc(null);
				}
				video.setSrcOutput(null);
				video.setHash(null);
			}
		}
		videoQR = null;
		userQRCode = null;
		videoQRFactory = null;
		//videoFactory = null;
		userQRCodeFactory = null;
		
		
		
		Videoplay videoplay = videoplayService.findByIdOfContent(id_of_content, currentUser.getId());

		if(videoplay == null) {
			Videoplay newVideoplay = new Videoplay();
			newVideoplay.setId_of_user(currentUser.getId());
			newVideoplay.setId_of_course(id_of_content);
			newVideoplay.setNumber(1);
			newVideoplay.setDate_of_add(ptsc.parseTimestamp(timestamp));
			newVideoplay.setDate_of_refresh(ptsc.parseTimestamp(timestamp));
			videoplayService.save(newVideoplay);
			newVideoplay = null;
		} else {
			videoplay.setNumber(number);
			videoplay.setDate_of_refresh(ptsc.parseTimestamp(timestamp));
			videoplayService.save(videoplay);
			videoplay = null;
		}
		
		
		Accessofuser accessofuser = accessofuserService.findActiveByIdOfContent(currentUser.getId(), id_of_content, 0);
		if(accessofuser == null) {
			answer = new Json("error","no_access", null);
			return answer;
		} else {
			if(accessofuser.getDate_of_activation() == 0) {
				
				String timestampYMD = LocalDateTime.now(ZoneId.of("UTC+06:00"))
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				int tsYMD = (int)(ptsc.parseDatestamp(timestampYMD).getTime() / 1000L);
				
				accessofuser.setDate_of_activation(tsYMD);
				accessofuser.setDate_must_be_used(tsYMD + 86400 * accessofuser.getDays());
				accessofuserService.save(accessofuser);
				
				timestampYMD = null;
			}
		}
		
		currentUserName = null;
		currentUser = null;
		timestamp = null;
		ptsc = null;
		accessofuser = null;
		
		System.gc();
		
		answer = new Json("success","", listVideo);
		return answer;
	}
	

	@GetMapping("/account/updatevideoplay")
	@ResponseBody
	public Json updatevideoplay(
			@RequestParam(defaultValue = "0") int id_of_content,
			@RequestParam(defaultValue = "0") int number
			) {
		Json answer = new Json();
		if(id_of_content == 0 || number == 0 ) {
			answer = new Json("error","no_data", null);
			return answer;
		}

		String currentUserName = authenticationFacade.getAuthentication().getName();
		User currentUser = userService.findByUsername(currentUserName);

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		
		
		Videoplay videoplay = videoplayService.findByIdOfContent(id_of_content, currentUser.getId());
		if(videoplay == null) {
			videoplay = new Videoplay();
			videoplay.setId_of_user(currentUser.getId());
			
			
			videoplay.setId_of_course(id_of_content);
			
			videoplay.setNumber(1);
			videoplay.setDate_of_add(ptsc.parseTimestamp(timestamp));
			videoplay.setDate_of_refresh(ptsc.parseTimestamp(timestamp));
			videoplayService.save(videoplay);
		} else {
			videoplay.setNumber(number);
			videoplay.setDate_of_refresh(ptsc.parseTimestamp(timestamp));
			videoplayService.save(videoplay);
		}
		
		currentUserName = null;
		currentUser = null;
		timestamp = null;
		ptsc = null;
		videoplay = null;
		answer = null;
		System.gc();
		
		
		return null;
	}
}

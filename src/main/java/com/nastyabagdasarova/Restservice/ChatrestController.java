package com.nastyabagdasarova.Restservice;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nastyabagdasarova.Component.EmailThread;
import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Component.MD5;
import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.Course;
import com.nastyabagdasarova.Model.Back;
import com.nastyabagdasarova.Model.Chat;
import com.nastyabagdasarova.Model.Json;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.AccessofuserService;
import com.nastyabagdasarova.Service.CourseService;
import com.nastyabagdasarova.Service.BackService;
import com.nastyabagdasarova.Service.ChatService;
import com.nastyabagdasarova.Service.RandomService;
import com.nastyabagdasarova.Service.UserService;

@RestController
public class ChatrestController {

	@Autowired
	private UserService userService;
	@Autowired
	private BackService backService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private RandomService randomService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private AccessofuserService accessofuserService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
	
    private String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
    		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
    
	@GetMapping("/chat")
	@ResponseBody
	public Json getChat(@RequestParam(defaultValue = "0") int id_of_course) {
		
		Json answer = new Json();
		if(id_of_course == 0) {
			answer = new Json("error","no_data", null, "Administrator");
			return answer;
		}
		
		String currentUserName = authenticationFacade.getAuthentication().getName();
		User currentUser = userService.findByUsername(currentUserName);
		
		Accessofuser accessofuser = accessofuserService.findActiveByIdOfContent(currentUser.getId(), id_of_course, 1);
		if(accessofuser == null) {
			answer = new Json("error","no_access", null);
			return answer;
		}
		Back back = backService.findByIdOfContent(id_of_course);
		if(back == null) {
			answer = new Json("error","wrong_id");
			return answer;
		}
		
		List<Chat> chatList = chatService.findByIdOfBack(currentUser.getId(), back.getId(), 0);
		
		int i = 0;
		for(Chat chat : chatList) {
			if(chat.getUser0_admin1() == 1 && chat.getDate_of_readed() == null) {
				chat.setDate_of_readed(ptsc.parseTimestamp(timestamp));
				chatService.save(chat);
			}
			
			chat.setDate_of_add_to_print(new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(chat.getDate_of_add()));
			if(chat.getDate_of_readed() != null) {
				chat.setDate_of_readed_to_print(new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(chat.getDate_of_readed()));
			}
			
			chatList.set(i, chat);
			i++;
		}
		
		currentUserName = null;
		currentUser = null;
		accessofuser = null;
		back = null;
		System.gc();
		
		answer = new Json("success", "", chatList, "Administrator");
		
		return answer;
	}

	
	@PostMapping("/chat/send")
	@ResponseBody
	public Json send(
			@RequestParam(name = "userfile", required = false) MultipartFile userfile,
			@RequestParam(defaultValue = "0") int id_of_content,
			@RequestParam(defaultValue = "") String message
			) {
		//MultipartFile
		Json answer = new Json();
		if(id_of_content == 0 || message.equals("")) {
			userfile = null;
			message = null;
			System.gc();
			
			answer = new Json("error","no_data", null, "Administrator");
			return answer;
		}
		
		
		/*
		System.out.println("getFileSizeMegaBytes(userfile) = " + getFileSizeMegaBytes(userfile));
		if(getFileSizeMegaBytes(userfile) > 200000000) {
			answer = new Json("error","wrong_check", null, "Администратор");
			return answer;
		}
		*/
		
		String currentUserName = authenticationFacade.getAuthentication().getName();
		User currentUser = userService.findByUsername(currentUserName);

		Accessofuser accessofuser = accessofuserService.findActiveByIdOfContent(currentUser.getId(), id_of_content, 1);
		if(accessofuser == null) {
			userfile = null;
			message = null;
			currentUserName = null;
			currentUser = null;
			System.gc();
			
			answer = new Json("error","no_access", null);
			return answer;
		}
		Back back = backService.findByIdOfContent(id_of_content);
		if(back == null) {
			userfile = null;
			message = null;
			currentUserName = null;
			currentUser = null;
			back = null;
			System.gc();
			
			answer = new Json("error","wrong_id", null, "Administrator");
			return answer;
		}
		
		List<Chat> chatList = chatService.findByIdOfBack(currentUser.getId(), back.getId(), 1);
		if(chatList.size() > 10) {
			userfile = null;
			message = null;
			currentUserName = null;
			currentUser = null;
			back = null;
			chatList = null;
			System.gc();
			
			answer = new Json("error","wait_of_answer", null, "Administrator");
			return answer;
		}

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		
		
		Chat newChat = new Chat();
		newChat.setId_of_back(back.getId());
		newChat.setFrom_who(currentUser.getId());
		newChat.setMessage(message);
		newChat.setUser0_admin1(0);
		newChat.setIs_file(0);
		newChat.setDate_of_add(ptsc.parseTimestamp(timestamp));
		
		String subject = "Контент по курсу: id"+id_of_content+" - " + courseService.findById(id_of_content).getName();
		
		chatService.save(newChat);
		
		String hash = "";
		try {
			hash = MD5.md5(MD5.md5(MD5.md5(back.getId() + "fdgfhKUJHFDUFHsi74t3t4gsdrsdggrd")));
		} catch (NoSuchAlgorithmException ex) {
			ex.getMessage();
			userfile = null;
			message = null;
			currentUserName = null;
			currentUser = null;
			back = null;
			chatList = null;
			newChat = null;
			subject = null;
			hash = null;
			System.gc();
			
			answer = new Json("error","mail_error", null, "Administrator");
			return answer;
		}
		
		String mailMessage = "<b>От кого: </b>"+currentUser.getFio()+" (id"+currentUser.getId()+" - "+currentUser.getUsername()+")<br />" +
	            "<b>Дата отправки:</b> "+timestamp+"<br />" +
	            "<b>Контент вопроса:</b><br />" + message +
	            "<br /><br /><center>"+
	            "<a href='"+FinalComponent.documentRoot+"/chat/admin?id_of_back="+back.getId()+
	            "&id_of_user=" +currentUser.getId() +
	            "&hash="+ hash +"' target='_blank'>"
	            + "<button style='background-color:green;color:#fff;padding: 6px 12px;border-radius: 15px;box-shadow: none;'>"
	            + "Шифрованный канал связи</button></a>"
	            + "</center>"
	            + "<img src='"+FinalComponent.documentRoot+"/chat/readfrommail?m="+newChat.getId()+"' style='display:none;' />";
		
		
		
		if(userfile.isEmpty() || userfile == null) {
			EmailThread emailThread = new EmailThread(currentUser.getId(), FinalComponent.emailDeveloper, "Вопрос: " + subject,
					"<i>К письму должен идти файл! Если файл не пришел, сообщите пожалуйста администраторам платформы.</i><br />" + 
							mailMessage, null, null);
			emailThread.start();
			
		} else {
			newChat.setIs_file(1);
			chatService.save(newChat);

			EmailThread emailThread = new EmailThread(currentUser.getId(), FinalComponent.emailDeveloper, "Вопрос: " + subject,
					"<i>К письму должен идти файл! Если файл не пришел, сообщите пожалуйста администраторам платформы.</i><br />" + 
							mailMessage, userfile, currentUser.getId().toString() + "_" + randomService.generatedRandomString(6));
			emailThread.start();
			
		}

		userfile = null;
		message = null;
		currentUserName = null;
		currentUser = null;
		accessofuser = null;
		back = null;
		chatList = null;
		timestamp = null;
		ptsc = null;
		newChat = null;
		subject = null;
		hash = null;
		mailMessage = null;
		System.gc();
		
		
		
		
		answer = new Json("success","", null, "Administrator");
		return answer;
	}

	


	
	@GetMapping("/chat/readfrommail")
	@ResponseBody
	public void readfrommail(
		@RequestParam(defaultValue = "0") int m
		) {
		
		Chat chat = chatService.findById(m);
		if(!(chat == null)) {
			chat.setDate_of_readed(ptsc.parseTimestamp(timestamp));
			chatService.save(chat);
		}
	}

	
	
	
	
	

    
	@PostMapping("/chat/adminindex")
	@ResponseBody
	public Json getChat(
			@RequestParam(defaultValue = "0") int id_of_back,
			@RequestParam(defaultValue = "0") int id_of_user,
			@RequestParam(defaultValue = "") String hash
			) throws NoSuchAlgorithmException {
		Json answer = new Json();
		if(id_of_back == 0 || id_of_user == 0) {
			answer = new Json("error","no_data", null, "Администратор");
			return answer;
		}
		
		if(!hash.equals(MD5.md5(MD5.md5(MD5.md5(id_of_back +"fdgfhKUJHFDUFHsi74t3t4gsdrsdggrd"))))) {
			
		}
		
		User user = userService.findById(id_of_user);
		if(user == null) {
			answer = new Json("error","wrong_id", null, "Администратор");
			return answer;
		}
		
		Back back = backService.findById(id_of_back);
		if(back == null) {
			answer = new Json("error","wrong_id", null, "Администратор");
			return answer;
		}
		
		
		List<Chat> chatList = chatService.findByIdOfBack(user.getId(), back.getId(), 0);
		
		int i = 0;
		for(Chat chat : chatList) {
			if(chat.getUser0_admin1() == 0 && chat.getDate_of_readed() == null) {
				chat.setDate_of_readed(ptsc.parseTimestamp(timestamp));
				chatService.save(chat);
			}
			
			chat.setDate_of_add_to_print(new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(chat.getDate_of_add()));
			if(chat.getDate_of_readed() != null) {
				chat.setDate_of_readed_to_print(new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(chat.getDate_of_readed()));
			}
			
			chatList.set(i, chat);
			i++;
		}
		
		answer = new Json("success", "", chatList, "Администратор");
		answer.setFrom_who(user.getFio());
		return answer;
	}

	
	@PostMapping("/chat/adminsend")
	@ResponseBody
	public Json adminSend(
			@RequestParam(name = "userfile", required = false) MultipartFile userfile,
			@RequestParam(defaultValue = "0") int id_of_back,
			@RequestParam(defaultValue = "0") int id_of_user,
			@RequestParam(defaultValue = "") String message,
			@RequestParam(defaultValue = "") String hash
			) {
		//MultipartFile
		Json answer = new Json();
		if(id_of_back == 0 || id_of_user == 0) {
			userfile = null;
			message = null;
			hash = null;
			System.gc();
			
			answer = new Json("error","no_data", null, "Администратор");
			return answer;
		}
		
		User user = userService.findById(id_of_user);
		if(user == null) {
			userfile = null;
			message = null;
			hash = null;
			user = null;
			System.gc();
			
			answer = new Json("error","wrong_id", null, "Администратор");
			return answer;
		}
		
		Back back = backService.findById(id_of_back);
		if(back == null) {
			userfile = null;
			message = null;
			hash = null;
			user = null;
			back = null;
			System.gc();
			
			answer = new Json("error","wrong_id", null, "Администратор");
			return answer;
		}
		

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		
		
		Chat newChat = new Chat();
		newChat.setId_of_back(back.getId());
		newChat.setTo_who(user.getId());
		newChat.setMessage(message);
		newChat.setUser0_admin1(1);
		newChat.setIs_file(0);
		newChat.setDate_of_add(ptsc.parseTimestamp(timestamp));
		
		String subjectToUser = "No theme";
		String subjectToAdmin = "Тема не указана";
		if(back.getId_of_course() != 0) {
			Course contentInfo = courseService.findById(back.getId_of_course());
			subjectToUser = "Answer from administrator: (id"+contentInfo.getId()+") - " + contentInfo.getName();
			subjectToAdmin = "Ответ пользователю от администратора (id"+contentInfo.getId()+") - " + contentInfo.getName();
			contentInfo = null;
		}
		
		chatService.save(newChat);
		
		
		@SuppressWarnings("unused")
		String newHash = "";
		try {
			newHash = MD5.md5(MD5.md5(MD5.md5(back.getId() + "fdgfhKUJHFDUFHsi74t3t4gsdrsdggrd")));
		} catch (NoSuchAlgorithmException ex) {
			ex.getMessage();
			userfile = null;
			message = null;
			hash = null;
			user = null;
			back = null;
			timestamp = null;
			ptsc = null;
			newChat = null;
			subjectToUser = null;
			subjectToAdmin = null;
			newHash = null;
			System.gc();
			
			answer = new Json("error","mail_error", null, "Администратор");
			return answer;
		}
		
		String mailMessageToAdmin = "<b>От кого: </b>Администратор<br />" +
	            "<b>Дата отправки:</b> "+timestamp+"<br />" +
	            "<b>Кому: </b>"+user.getFio()+" (id"+user.getId()+" - "+user.getUsername()+")<br />"+
	            "<b>Ответ:</b><br />" + message +
	            "<br /><br /><center>"+
	            "<a href='https://nastyabagdasarova.com/chat/admin?id_of_back="+back.getId()+
	            "&id_of_user=" +user.getId() +
	            "&hash="+ hash +" target='_blank'>"
	            + "<button style='background-color:green;color:#fff;padding: 6px 12px;border-radius: 15px;box-shadow: none;'>"
	            + "Шифрованный канал связи</button></a>"
	            + "</center>"
	            + "<img src='https://nastyabagdasarova.com/chat/readfrommail?m="+newChat.getId()+"' style='display:none;' />";
		
		String mailMessageToUser = "<b>From who: </b>Administrator<br />" +
	            "<b>Send date:</b> "+timestamp+"<br />" +
	            "<b>To who: </b>"+user.getFio()+" ("+user.getUsername()+")<br />"+
	            "<b>Message:</b><br />" + message +
	            "<br /><br /><center>"
				+ "<img src='"+FinalComponent.documentRoot+"/images/logo.png' style='width:50px;height:50px;margin:25px 0;' /><br /><br />"
				+ "<a href='"+FinalComponent.documentRoot+"/account' target='_blank'>"
				+ "<button style='background-color:green;color:#fff;padding: 6px 12px;border-radius: 15px;box-shadow: none;'>"
				+ "To account</button></a>"
	            + "</center>"
	            + "<img src='"+FinalComponent.documentRoot+"/chat/readfrommail?m="+newChat.getId()+"' style='display:none;' />";
		
		
	
		if(userfile.isEmpty()) {

			EmailThread emailThreadToAdmin = new EmailThread(user.getId(), FinalComponent.emailDeveloper, subjectToAdmin,
					"<i>К письму должен идти файл! Если файл не пришел, сообщите пожалуйста администраторам платформы.</i><br />" + 
							mailMessageToAdmin, null, null);
			emailThreadToAdmin.start();

			EmailThread emailThreadToUser = new EmailThread(user.getId(), user.getUsername(), subjectToUser,
					"<i>The file must be get with this mail! If there is no file, please inform the platform administrators through " 
					+"contacts or social network.</i><br />" + mailMessageToUser, null, null);
			emailThreadToUser.start();
			
			
		} else {
			newChat.setIs_file(1);
			chatService.save(newChat);
			//String randomString = randomService.generatedRandomString(6);
			
			//es.sendMessageWithAttechment(user.getUsername(), subjectToUser, "<i>К письму должен идти файл! Если файл не пришел, сообщите пожалуйста администраторам платформы.</i><br />" + 
			//mailMessageToUser, userfile, randomString);
			
			EmailThread emailThread = new EmailThread(user.getId(), FinalComponent.emailDeveloper, subjectToAdmin,
					"<i>К письму должен идти файл! Если файл не пришел, сообщите пожалуйста администраторам платформы.</i><br />" + 
							mailMessageToAdmin, userfile, "admin_to_" + user.getId().toString());
			emailThread.start();
			
			//randomString = randomService.generatedRandomString(6);
			EmailThread emailThread2 = new EmailThread(user.getId(), user.getUsername(), subjectToUser,
					"<i>The file must be get with this mail! If there is no file, please inform the platform administrators through "
					+ "contacts or social network.</i><br />" + 
							mailMessageToUser, userfile, "admin_to_" + user.getId().toString() + "_2");
			emailThread2.start();
			
			//randomString = null;
		}
		
		
		answer = new Json("success","", null, "Администратор");
		answer.setFrom_who(user.getFio());
		
		
		userfile = null;
		message = null;
		hash = null;
		user = null;
		back = null;
		timestamp = null;
		ptsc = null;
		newChat = null;
		subjectToUser = null;
		subjectToAdmin = null;
		newHash = null;
		mailMessageToAdmin = null;
		mailMessageToUser = null;
		System.gc();
		
		
		
		
		
		
		return answer;
	}
	
	
}

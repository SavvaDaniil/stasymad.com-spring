package com.nastyabagdasarova.Restservice;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Component.EmailThread;
import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Model.Contact;
import com.nastyabagdasarova.Model.Json;
import com.nastyabagdasarova.Service.ContactService;

@RestController
public class ContactsRestController {

	@Autowired
	ContactService contactService;

	@PostMapping("/contacts/send")
	@ResponseBody
	public Json Index(
			@RequestParam(defaultValue = "") String mail,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String message
			) {
		Json answer = new Json();

		if(mail.equals("") || name.equals("") || message.equals("")) {
			mail = null;
			name = null;
			message = null;
			System.gc();
			
			answer = new Json("error","no_data");
			return answer;
		}
		
		if(contactService.countByDay() > 20) {
			answer = new Json("error","too_much_messages");
			return answer;
		}
		
		
		
		String ip = "не перехвачен";
		Contact contact = new Contact(mail, name, message);
		try(
			final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (SocketException e) {
			e.printStackTrace();

			mail = null;
			name = null;
			message = null;
			contact = null;
			System.gc();
			
			answer = new Json("error","unknown_error");
			return answer;
		} catch (UnknownHostException e) {
			e.printStackTrace();

			mail = null;
			name = null;
			message = null;
			contact = null;
			System.gc();
			
			answer = new Json("error","unknown_error");
			return answer;
		}
		contact.setIp(ip);
		
		contactService.save(contact);
		
		EmailThread emailThread = new EmailThread(null, FinalComponent.emailDeveloper, "Сообщение из контактов",
				"<p><b>mail:</b> "+ mail +"</p>"
				+ "<p><b>name:</b> "+ name +"</p>"
				+ "<p><b>ip:</b> "+ ip +"</p>"
				+ "<p><b>message:</b> "+ message +"</p>"
				, null, null);
		emailThread.start();

		mail = null;
		name = null;
		message = null;
		contact = null;
		System.gc();
		
		return new Json("success","");
	}
}

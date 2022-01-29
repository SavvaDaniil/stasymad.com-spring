package com.nastyabagdasarova.servingwebcontent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Component.PayPalClient;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Listener.MainEventListener;
import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.Cart;
import com.nastyabagdasarova.Model.Paymentapp;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.AccessofuserService;
import com.nastyabagdasarova.Service.CartService;
import com.nastyabagdasarova.Service.PaymentService;
import com.nastyabagdasarova.Service.PreferenceService;
import com.nastyabagdasarova.Service.UserService;

@Controller
public class PaymentController {

	@Autowired
	private UserService userService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PreferenceService preferenceService;
	@Autowired
	private AccessofuserService accessofuserService;
	@Autowired
	private CartService cartService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    
    
	
	@GetMapping("/payment/prepare")
	public String prepare(Model model, HttpServletRequest request) {
		String title = "Payment processing... " + FinalComponent.end_of_title;
		model.addAttribute("title", title);
		
		boolean isAuth = true;
		model.addAttribute("isAuth", isAuth);
		
		User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		List<Cart> cartList = cartService.getCartOfUser(user.getId());
		if(cartList.isEmpty()) {
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/cart";
		}


		//String timestamp = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
	    //        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		//System.out.println("timestamp = " + timestamp);
		
		Paymentapp payment = paymentService.findByIdOfUser(user.getId());
		if(payment == null) {
			payment = new Paymentapp();
			payment.setId_of_user(user.getId());
			payment.setDate_of_add(ptsc.parseTimestamp(timestamp));
			payment.setStatus(0);
			paymentService.save(payment);
		}
		
		
		
		Integer summ = 0;
		int i = 0;
		for(Cart cart : cartList) {
			cart.setId_of_payment(payment.getId());
			cartService.save(cart);
			
			
			summ += cart.getPrice();
			i++;
		}
		payment.setSumma(summ);
		//paymentService.save(payment);
		
		model.addAttribute("summ", summ.toString());
		
		

		
		
		
		/*
		String mrh_login = "";
		String mrh_pass1 = "";
		int out_summ = summ;
		int inv_id = 0;
		String inv_desc = "";
		
		//$crc  = md5("$mrh_login:$out_summ:$inv_id:$mrh_pass1");
		String crc;
		try {
			crc = MD5.md5(mrh_login+":"+out_summ+":"+inv_id+":"+mrh_pass1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "redirect:" + request.getScheme() +"://" + request.getServerName() + ":8080/payment/fail";
		}
		
		int IsTest = 0;
		
		
		String url = "https://auth.robokassa.ru/Merchant/Index.aspx?MerchantLogin="+mrh_login+"&"
				+ "OutSum="+out_summ+"&InvId="+inv_id+"&Description="+inv_desc+"&SignatureValue="+crc+"&IsTest="+IsTest;
		model.addAttribute("url", url);
		url = null;
		*/
		
		PayPalClient payPalClient = new PayPalClient();
		model.addAttribute("paypal_link", payPalClient.createPayment(summ.toString(), payment));
		paymentService.save(payment);
		
		
		timestamp = null;
		ptsc = null;
		title =null;
		cartList = null;
		payment = null;
		
		payPalClient = null;
		System.gc();
		
		
		
		return "payment_prepare";
	}
	

	@GetMapping("/payment/result")
	public String result(HttpServletRequest request) {
		
		/*
		String mrh_pass2 = "";
		
		if(InvId == 0) {
			return "no_id";
		}
		
		//$my_crc = strtoupper(md5("$out_summ:$inv_id:$mrh_pass2"));
		String crc = null;
		try {
			crc = (MD5.md5(OutSum+":"+InvId+":"+mrh_pass2)).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error crypto\n";
		}
		//System.out.println("Подпись должна быть " + crc);
		if(!crc.equals(SignatureValue)) {
			//return "bad sign\n";
		}
		
		
		Paymentapp payment = paymentService.findByIdNotPayed(InvId);
		if(payment == null) {
			return "not_found";
		}
		*/
		MainEventListener mainEventListener = new MainEventListener();
		
		User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		Paymentapp payment = paymentService.findByPaypalPaymentIdOrReturnNull(user.getId(), request.getParameter("paymentId"));
		if(payment == null) {
			mainEventListener.updateToEverybody("Ошибка, на найден платеж при попытке оплаты", ""
					+ "<p>Пользователь: ("+user.getId()+") "+ user.getUsername() +" </p>"
					+ "<p>Полученный paymentId:"+request.getParameter("paymentId").toString()+"</p>");
			mainEventListener = null;
			user = null;
			payment = null;
			System.gc();
			//return "payment_not_found";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/payment/fail";
		}
		
		
		//проверяем на правильность
		PayPalClient payPalClient = new PayPalClient();
		Map<String, Object> completePayment = payPalClient.completePayment(request);
		if(completePayment == null) {
			mainEventListener.updateToEverybody("Ошибка от PayPal при попытке оплаты",  ""
					+ "<p>Пользователь: ("+user.getId()+") "+ user.getUsername() +" </p>"
					+ "<p>Полученный paymentId:"+request.getParameter("paymentId").toString()+"</p>"
					+ "<p>Платеж: "+payment.getId()+"</p>");
			mainEventListener = null;
			user = null;
			payment = null;
			payPalClient = null;
			System.gc();
			//return "error_payment";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/payment/fail";
		}
		if(!completePayment.get("status").toString().equals("success")) {
			mainEventListener.updateToEverybody("Ошибка от PayPal при попытке оплаты, completePayment != success",  ""
					+ "<p>Пользователь: ("+user.getId()+") "+ user.getUsername() +" </p>"
					+ "<p>Полученный paymentId:"+request.getParameter("paymentId").toString()+"</p>"
					+ "<p>Платеж: "+payment.getId()+"</p>");
			mainEventListener = null;
			user = null;
			payment = null;
			payPalClient = null;
			completePayment = null;
			System.gc();
			//return "error_payment";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/payment/fail";
		}
		
		
		

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		
		
		
		
		List<Cart> cartList = cartService.getCartOfIdOfPayment(payment.getId());
		for(Cart cart : cartList) {
			
			if(cart.getId_of_course() != 0) {
				Accessofuser accessofuser = new Accessofuser();
				accessofuser.setId_of_user(cart.getId_of_user());
				accessofuser.setId_of_payment(payment.getId());
				accessofuser.setStatus(1);
				accessofuser.setDate_of_add(ptsc.parseTimestamp(timestamp));
				accessofuser.setId_of_course(cart.getId_of_course());
				accessofuser.setIs_back(cart.getIs_back());
				accessofuser.setDays(cart.getDays());
				accessofuser.setOperation(cart.getOperation());
				
				if(preferenceService.isUseAutoVideoQRGenerator().equals("1")) {
					accessofuser.setIsVideoQr(1);
				}
				
				accessofuserService.save(accessofuser);
			}
			
			cartService.deleteById(cart.getId());
		}
		
		
		payment.setStatus(1);
		payment.setDate_of_done(ptsc.parseTimestamp(timestamp));
		paymentService.save(payment);


		mainEventListener.updateToEverybody("Успешная оплата через PayPal",  ""
				+ "<p>Пользователь: ("+user.getId()+") "+ user.getUsername() +" </p>"
				+ "<p>Полученный paymentId:"+request.getParameter("paymentId").toString()+"</p>"
				+ "<p>Платеж: "+payment.getId()+"</p>"
				+ "<p>Сумма: "+payment.getSumma()+"$</p>"
		);
		
		//обращение к генератору VideoQR
		if(preferenceService.isUseAutoVideoQRGenerator().equals("1")) {
			
		}
		
		
		mainEventListener = null;
		user = null;
		payment = null;
		payPalClient = null;
		completePayment = null;
		timestamp = null;
		ptsc = null;
		cartList = null;
		System.gc();
		
		
		return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/payment/success";
	}
	
	
	@GetMapping("/payment/success")
	public String success(Model model, HttpServletRequest request) {
		String title = "Success payment " + FinalComponent.end_of_title;
		model.addAttribute("title", title);
		
		//пример полученного
		//paymentId=PAYID-L6R7TWI7ND792831P783010B&token=EC-5NB19182SN2074006&PayerID=CMD4C7DDA3YQS
		
		
		
		boolean isAuth = true;
		model.addAttribute("isAuth", isAuth);
		return "payment_success";
	}
	
	@GetMapping("/payment/fail")
	public String fail(Model model) {
		String title = "Failed payment " + FinalComponent.end_of_title;
		model.addAttribute("title", title);
		boolean isAuth = true;
		model.addAttribute("isAuth", isAuth);
		return "payment_fail";
	}
}

package com.nastyabagdasarova.Restservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


@RestController
public class PaymentRestController {
	/*
	@Autowired
	private UserService userService;
	@Autowired
	private PreferenceService preferenceService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private CartService cartService;
	@Autowired
	private AccessofuserService accessofuserService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    */
}

package com.nastyabagdasarova.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.nastyabagdasarova.Model.Paymentapp;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Component
public class PayPalClient {

	private final String clientId = "XXX";
	private final String clientSecret = "XXX";
	
	
	public Map<String, Object> createPayment(String sum, Paymentapp paymentapp){
		
		...
		
	    try {
			
	        
	    } catch (PayPalRESTException e) {
	        System.out.println("Error happened during payment creation!");
	    }
	    
	    
	    //createdPayment = null;
	    System.gc();
	    
	    
	    return response;
	}
	
	public Map<String, Object> completePayment(HttpServletRequest req){
		...
		
	    return response;
	}
}

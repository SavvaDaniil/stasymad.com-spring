package com.nastyabagdasarova.Service;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomService {
	public String generatedRandomString(int value) {
		char arrayValues[] = {
			'1','2','3','4','5',
			 '6','7','8','9','0',
			 'a','b','c','d','e','f',
			 'g','h','i','j','k','l',
			 'm','n','o','p','r','s',
			 't','u','v','x','y','z',
			 '1','2','3','4','5','6',
			 '7','8','9','0'
		};
		int rnd;
		StringBuffer newPassword = new StringBuffer();
		for(int i = 0; i < value; i++) {
			rnd = new Random().nextInt(arrayValues.length);
			newPassword.append(arrayValues[rnd]);
		}
		return newPassword.toString();
	}
}

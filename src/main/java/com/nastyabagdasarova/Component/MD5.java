package com.nastyabagdasarova.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String md5(String a) throws NoSuchAlgorithmException {
		MessageDigest md_md5 = MessageDigest.getInstance("MD5");
		byte[] bytes = md_md5.digest(a.getBytes());
		
		BigInteger bigInt = new BigInteger(1, bytes);
	    String md5Hex = bigInt.toString(16);
	    while( md5Hex.length() < 32 ){
	        md5Hex = "0" + md5Hex;
	    }
	    md_md5 = null;
	    bytes = null;
	    bigInt = null;
	    System.gc();
	    
	    return md5Hex;
	}
}

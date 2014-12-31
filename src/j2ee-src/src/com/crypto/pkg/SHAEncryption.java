package com.crypto.pkg;

import java.security.MessageDigest;

public class SHAEncryption {

	public String encode(String pass) throws Exception {
		String password = pass;
		 
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(password.getBytes());
 
        byte byteData[] = md.digest();
 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        return sb.toString();
 
	}
	
}

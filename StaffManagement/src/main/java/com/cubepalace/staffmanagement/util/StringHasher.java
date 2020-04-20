package com.cubepalace.staffmanagement.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHasher {

	String pass;
	byte[] salt;
	
	public StringHasher(String pass, byte[] salt) {
		this.pass = pass;
		this.salt = salt;
	}
	
	public String saltAndHash() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] hashPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));
			return new String(hashPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}

package com.it.xushuai.baseapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Ecrypt {

	private static MessageDigest digest;

	static {
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// better never happens
		}
	}

	/**
	 * Returns a 32 chararacter hexadecimal representation of an MD5 hash of the given String.
	 * 
	 * @param s the String to hash
	 * @return the md5 hash
	 */
	public static String md5(String s) {
		try {
			byte[] bytes = digest.digest(s.getBytes("UTF-8"));
			StringBuilder b = new StringBuilder(32);
			for (byte aByte : bytes) {
				String hex = Integer.toHexString((int) aByte & 0xFF);
				if (hex.length() == 1)
					b.append('0');
				b.append(hex);
			}
			return b.toString();
		} catch (UnsupportedEncodingException e) {
			// utf-8 always available
		}
		return null;
	}
}

package com.chinainpay.apps.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public final class EncryptUtil {

	/**
	 * MD5
	 */
	public static String MD5Encrypt(String s) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes("UTF-8"));
			result = byteToHex(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * SHA-1
	 */
	public static String sha1Encrypt(String s) {
		String result = null;
		try {
			MessageDigest algorithm = null;
			algorithm = MessageDigest.getInstance("SHA-1");
			algorithm.update(s.getBytes());
			result = byteToHex(algorithm.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String byteToHex(byte[] b) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * Echo Shal加密
	 * 
	 * @return
	 */
	public static String shal(String s) {
		String result = DigestUtils.shaHex(s);
		return result;
	}

}

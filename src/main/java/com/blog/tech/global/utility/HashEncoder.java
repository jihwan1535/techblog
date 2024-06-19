package com.blog.tech.global.utility;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashEncoder {

	private static final String SECRET_KEY = "THIS-IS-INTERNET-DATA-BASE-TEST-SECRET-KEY";

	public static String generateHash(final String data) {
		try {
			final SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

			final Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);

			final byte[] macData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(macData);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String bytesToHex(byte[] hash) {
		final StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			final String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

}

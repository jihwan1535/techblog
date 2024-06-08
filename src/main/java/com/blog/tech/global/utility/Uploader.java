package com.blog.tech.global.utility;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import jakarta.servlet.http.Part;

public class Uploader {

	public static final String SYSTEM_PATH = System.getProperty("user.home");
	public static final String SLASH = File.separator;
	public static final String IMAGE_PATH = SLASH + "upload" + SLASH + "images";
	public static final String PROFILE_PATH = IMAGE_PATH + SLASH + "profile" + SLASH;
	public static final String POST_PATH = IMAGE_PATH + SLASH + "posts" + SLASH;
	public static final String PROFILE_URL = "/upload/images/profile/";
	public static final String POST_URL = "/upload/images/posts/";
	private static final String URL = "http://localhost:8888";
	public static final String DEFAULT_IMAGE = "http://localhost:8888/" + PROFILE_URL + "profile.png";

	public static String profileImageUpload(final Part image, final String nickname) {
		final String hashPath = generateHash(nickname);
		final String imageSavePath = SYSTEM_PATH + PROFILE_PATH + hashPath + SLASH;
		makeDirectory(imageSavePath);

		final String saveImageName = parseSaveFileName(image);
		final File uploadPath = new File(imageSavePath, saveImageName);
		transferFile(image, uploadPath);

		return URL + PROFILE_URL + hashPath + "/" + saveImageName;
	}

	public static String postImageUpload(final Part image, final String nickname) {
		final String hashPath = generateHash(nickname);
		final String imageSavePath = SYSTEM_PATH + POST_PATH + hashPath + SLASH;
		makeDirectory(imageSavePath);

		final String saveImageName = parseSaveFileName(image);
		final File uploadPath = new File(imageSavePath, saveImageName);
		transferFile(image, uploadPath);

		return URL + POST_URL + hashPath + "/" + saveImageName;
	}

	private static String generateHash(String nickname) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(nickname.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 바이트 배열을 16진수 문자열로 변환
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private static void makeDirectory(final String fileSavePath) {
		final File directory = new File(fileSavePath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	private static String parseSaveFileName(final Part file) {
		final String fileName = file.getSubmittedFileName();
		final String extension = fileName.substring(fileName.lastIndexOf("."));
		final String fileBaseName = UUID.randomUUID().toString().substring(0, 8);

		return fileBaseName + "_" + System.currentTimeMillis() + extension;
	}

	private static void transferFile(final Part file, final File uploadPath) {
		try {
			file.write(uploadPath.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

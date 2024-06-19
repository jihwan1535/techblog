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
	public static final String FILE_PATH = SLASH + "upload" + SLASH + "files";
	public static final String PROFILE_IMAGE_PATH = IMAGE_PATH + SLASH + "profile" + SLASH;
	public static final String POST_FILE_PATH = FILE_PATH + SLASH + "posts" + SLASH;
	public static final String POST_IMAGE_PATH = IMAGE_PATH + SLASH + "posts" + SLASH;
	public static final String PROFILE_URL = "/upload/images/profile/";
	public static final String POST_URL = "/upload/images/posts/";
	public static final String POST_FILE_URL = "/upload/files/posts/";
	private static final String URL = "http://localhost:8888";
	public static final String DEFAULT_IMAGE = URL + PROFILE_URL + "profile.png";

	public static String profileImageUpload(final Part image, final String nickname) {
		final String hashPath = HashEncoder.generateHash(nickname);
		final String imageSavePath = SYSTEM_PATH + PROFILE_IMAGE_PATH + hashPath + SLASH;
		makeDirectory(imageSavePath);

		final String saveImageName = parseSaveFileName(image);
		final File uploadPath = new File(imageSavePath, saveImageName);
		transferFile(image, uploadPath);

		return URL + PROFILE_URL + hashPath + "/" + saveImageName;
	}

	public static String postImageUpload(final Part image, final String nickname) {
		final String hashPath = HashEncoder.generateHash(nickname);
		final String imageSavePath = SYSTEM_PATH + POST_IMAGE_PATH + hashPath + SLASH;
		makeDirectory(imageSavePath);

		final String saveImageName = parseSaveFileName(image);
		final File uploadPath = new File(imageSavePath, saveImageName);
		transferFile(image, uploadPath);

		return URL + POST_URL + hashPath + "/" + saveImageName;
	}

	public static String postFileUpload(final Part file, final String nickname) {
		final String hashPath = HashEncoder.generateHash(nickname);
		final String fileSavePath = SYSTEM_PATH + POST_FILE_PATH + hashPath + SLASH;
		makeDirectory(fileSavePath);

		final String saveImageName = parseSaveFileName(file);
		final File uploadPath = new File(fileSavePath, saveImageName);
		transferFile(file, uploadPath);

		return URL + POST_FILE_URL + hashPath + "/" + saveImageName;
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

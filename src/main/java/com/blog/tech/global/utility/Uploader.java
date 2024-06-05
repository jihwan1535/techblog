package com.blog.tech.global.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.http.Part;

public class Uploader {

	public static final String SYSTEM_PATH = System.getProperty("user.home");
	public static final String SLASH = File.separator;
	public static final String UPLOAD_DIR = SLASH + "upload";
	public static final String TEMP_DIR = SLASH + "temp";
	public static final String IMAGE_DIR = SLASH + "images";
	public static final String FILE_DIR =  SLASH + "files";
	private static final String URL = "http://localhost:8888";
	private static final String DEFAULT_IMAGE = "profile.png";

	public static String imageUpload(final Part temp) {
		final String tempImagePath = UPLOAD_DIR + IMAGE_DIR + TEMP_DIR;
		final String tempSavePath = SYSTEM_PATH + tempImagePath;
		makeDirectory(tempSavePath);

		final String saveTempName = parseSaveFileName(temp);
		final File uploadPath = new File(tempSavePath, saveTempName);
		transferFile(temp, uploadPath);

		return URL + tempImagePath + saveTempName;
	}

	public static String imageSave(final String newImageUrl, final String originalImageUrl) {
		final String imageName = newImageUrl.substring(newImageUrl.lastIndexOf(SLASH) + 1);
		if (imageName.equals(DEFAULT_IMAGE)) {
			deleteFile(originalImageUrl);
			return newImageUrl;
		}
		if (newImageUrl.equals(originalImageUrl)) {
			return newImageUrl;
		}

		final String imagePath = SYSTEM_PATH + UPLOAD_DIR + IMAGE_DIR;
		saveFile(imageName, imagePath);

		if (!newImageUrl.equals(originalImageUrl)) {
			deleteFile(originalImageUrl);
		}
		return URL + UPLOAD_DIR + IMAGE_DIR + SLASH + imageName;
	}

	public static String imageSave(final String newImageUrl) {
		final String imageName = newImageUrl.substring(newImageUrl.lastIndexOf(SLASH) + 1);
		if (imageName.equals(DEFAULT_IMAGE)) {
			return newImageUrl;
		}

		final String imagePath = SYSTEM_PATH + UPLOAD_DIR + IMAGE_DIR;
		saveFile(imageName, imagePath);

		return URL + UPLOAD_DIR + IMAGE_DIR + SLASH + imageName;
	}

	private static void saveFile(final String imageName, final String imagePath) {
		final Path imageSavePath = Paths.get(imagePath + SLASH + imageName);
		final Path tempSavePath = Paths.get(imagePath + TEMP_DIR + SLASH + imageName);

		try {
			Files.move(tempSavePath, imageSavePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void deleteFile(final String originalImageUrl) {
		final String imageName = originalImageUrl.substring(originalImageUrl.lastIndexOf(SLASH) + 1);
		if (!imageName.equals(DEFAULT_IMAGE)) {
			final String imagePath = SYSTEM_PATH + UPLOAD_DIR + IMAGE_DIR;
			final Path imageSavedPath = Paths.get(imagePath + SLASH + imageName);

			try {
				Files.deleteIfExists(imageSavedPath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
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

		return SLASH + fileBaseName + "_" + System.currentTimeMillis() + extension;
	}

	private static void transferFile(final Part file, final File uploadPath) {
		try {
			file.write(uploadPath.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

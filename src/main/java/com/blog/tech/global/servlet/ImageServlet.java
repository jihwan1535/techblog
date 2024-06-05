package com.blog.tech.global.servlet;

import static com.blog.tech.global.utility.Uploader.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.blog.tech.global.utility.Uploader;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/upload/images/*")
public class ImageServlet extends HttpServlet {

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String defaultPath = SYSTEM_PATH + UPLOAD_DIR + IMAGE_DIR;
		final String location = req.getPathInfo();

		final Path image = Paths.get(defaultPath, location);
		if (!Files.exists(image)) {
			throw new RuntimeException("Not Found Image");
		}

		final String contentType = getServletContext().getMimeType(image.getFileName().toString());
		if (contentType == null || !contentType.startsWith("image")) {
			throw new RuntimeException("Not Found Image");
		}

		resp.reset();
		resp.setContentType(contentType);
		resp.setHeader("Content-Length", String.valueOf(Files.size(image)));

		Files.copy(image, resp.getOutputStream());
	}

}

package com.blog.tech.global.utility;

import static com.blog.tech.global.utility.Uploader.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/upload/files/*")
public class GetFileServlet extends HttpServlet {

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String defaultPath = SYSTEM_PATH + FILE_PATH + SLASH;
		final String location = req.getPathInfo();

		final Path file = Paths.get(defaultPath, location);
		if (!Files.exists(file)) {
			throw new RuntimeException("Not Found File");
		}

		final String contentType = getServletContext().getMimeType(file.getFileName().toString());

		resp.reset();
		resp.setContentType(contentType);
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName().toString() + "\"");

		Files.copy(file, resp.getOutputStream());
	}

}

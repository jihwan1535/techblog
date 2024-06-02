package com.blog.tech.global.servlet;

import java.io.IOException;

import com.blog.tech.global.utility.Uploader;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/uploader/image")
public class ImageUploaderServlet extends HttpServlet {

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Part image = req.getPart("image");
		final String saveUrl = Uploader.imageUpload(image);

		resp.setContentType("text/plain");
		resp.getWriter().write(saveUrl);
	}
	
}

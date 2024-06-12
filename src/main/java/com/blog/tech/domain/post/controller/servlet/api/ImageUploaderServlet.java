package com.blog.tech.domain.post.controller.servlet.api;

import java.io.IOException;

import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.global.utility.Uploader;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/api/uploader/images/posts")
public class ImageUploaderServlet extends HttpServlet {

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Part image = req.getPart("file");
		final HttpSession session = req.getSession(false);
		final MemberResponseBean member = (MemberResponseBean)session.getAttribute("member");
		final String saveUrl = Uploader.postImageUpload(image, member.nickname());

		resp.setContentType("text/plain");
		resp.getWriter().write(saveUrl);
	}
	
}

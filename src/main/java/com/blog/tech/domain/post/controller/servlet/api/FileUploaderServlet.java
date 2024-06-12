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
@WebServlet("/api/uploader/files/posts")
public class FileUploaderServlet extends HttpServlet {

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Part file = req.getPart("file");
		final HttpSession session = req.getSession(false);
		final MemberResponseBean member = (MemberResponseBean)session.getAttribute("member");
		final String saveUrl = Uploader.postFileUpload(file, member.nickname());

		resp.setContentType("text/plain");
		resp.getWriter().write(saveUrl);
	}

}

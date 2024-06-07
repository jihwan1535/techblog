package com.blog.tech.domain.member.controller.servlet.openapi;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.dto.response.ProfileResponseBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

	private MemberController memberController;
	private static final String MY_PROFILE = "/member/myProfile.jsp";
	private static final String OTHER_PROFILE = "/member/profile.jsp";

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		memberController = (MemberController)context.getAttribute("memberController");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String requestURI = req.getRequestURI();
		final String encodedNickname = requestURI.substring(requestURI.lastIndexOf('/') + 2);
		try {
			final String nickname = URLDecoder.decode(encodedNickname, StandardCharsets.UTF_8.toString());
			final ProfileResponseBean profile = memberController.profile(nickname);
			req.setAttribute("profile", profile);

			final HttpSession session = req.getSession(false);
			final MemberResponseBean member = (MemberResponseBean)session.getAttribute("member");
			if (Objects.isNull(session) || Objects.isNull(member)) {
				includeToProfile(req, resp, OTHER_PROFILE);
			} else if (member.nickname().equals(nickname)) {
				includeToProfile(req, resp, MY_PROFILE);
			} else {
				includeToProfile(req, resp, OTHER_PROFILE);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void includeToProfile(
		final HttpServletRequest req,
		final HttpServletResponse resp,
		final String url
	) throws ServletException, IOException {
		final RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.include(req, resp);
	}
}

package com.blog.tech.domain.member.controller.servlet.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.request.ProfileRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.dto.response.ProfileResponseBean;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/members/profile")
public class ProfileServlet  extends HttpServlet {

	private MemberController memberController;

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		memberController = (MemberController)context.getAttribute("memberController");
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final HttpSession session = req.getSession(false);
		final String nickname = req.getParameter("nickname");
		final String image = req.getParameter("image");
		final String aboutMe = req.getParameter("about_me");
		final ProfileRequestBean updateInfo = ProfileRequestBean.of(nickname, image, aboutMe);
		final MemberResponseBean member = (MemberResponseBean)session.getAttribute("member");
		final ProfileResponseBean profile = memberController.profileUpdate(member.id(), updateInfo);

		session.setAttribute("member", profile.member());
		final String contextPath = "/profile/@";
		final String profileNickName = profile.member().nickname();
		final String encodedNickname = URLEncoder.encode(profileNickName, StandardCharsets.UTF_8.toString());
		final String redirectUrl = contextPath + encodedNickname;
		resp.sendRedirect(redirectUrl);
	}

}

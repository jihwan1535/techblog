package com.blog.tech.domain.member.controller.servlet.openapi;

import java.io.IOException;
import java.util.Objects;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.AuthorizationResponse;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/auth")
public class AuthorizationServlet extends HttpServlet {

	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		objectMapper = (ObjectMapper)context.getAttribute("objectMapper");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final HttpSession session = req.getSession(false);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		if(Objects.isNull(session)) {
			final String json = objectMapper.writeValueAsString(new AuthorizationResponse(0L));
			resp.getWriter().write(json);
		} else {
			final Object sessionAttr = session.getAttribute("member");
			if (sessionAttr == null) {
				final String json = objectMapper.writeValueAsString(new AuthorizationResponse(-1L));
				resp.getWriter().write(json);
			} else {
				final MemberResponseBean member = (MemberResponseBean)sessionAttr;
				final String json = objectMapper.writeValueAsString(new AuthorizationResponse(member.id()));
				resp.getWriter().write(json);
			}
		}
	}
	
}

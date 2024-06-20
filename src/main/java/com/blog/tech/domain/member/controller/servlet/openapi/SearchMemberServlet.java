package com.blog.tech.domain.member.controller.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.SearchMemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/search/members")
public class SearchMemberServlet extends HttpServlet {

	private MemberController memberController;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext servletContext = this.getServletContext();
		this.memberController = (MemberController)servletContext.getAttribute("memberController");
		this.objectMapper = (ObjectMapper)servletContext.getAttribute("objectMapper");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String nickname = req.getParameter("nickname");
		try {
			final List<SearchMemberResponse> members = memberController.searchMember(nickname);
			final String json = objectMapper.writeValueAsString(members);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
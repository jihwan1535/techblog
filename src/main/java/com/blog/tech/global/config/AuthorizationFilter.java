package com.blog.tech.global.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthorizationFilter implements Filter {

	private final String NO_LOGIN_MESSAGE = "Invalid Access: 로그인을 해주세요.";
	private final String NO_MEMBER_MESSAGE = "Invalid Access: 사용자 정보를 찾을 수 없습니다.";
	private final String INVALID_SESSION_MESSAGE = "Invalid Access: 유효하지 않은 세션입니다.";
	private final String DORMANCY_MESSAGE = "Invalid Access: 휴면해제를 해주세요.";
	private final String UNREGISTER_MESSAGE = "Invalid Access: 탈퇴 처리를 진행중인 회원입니다.";

	@Override
	public void doFilter(
		final ServletRequest servletRequest,
		final ServletResponse servletResponse,
		final FilterChain filterChain
	) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) servletRequest;
		final HttpServletResponse resp = (HttpServletResponse) servletResponse;
		final HttpSession session = req.getSession(false);

		if (Objects.isNull(session)) {
			forwardToAlert(session, req, resp, INVALID_SESSION_MESSAGE);
		} else {
			final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");

			if (Objects.isNull(member)) {
				forwardToAlert(session, req, resp, NO_LOGIN_MESSAGE);
			} else if (member.status().equals(MemberStatus.DORMANCY)) {
				forwardToAlert(session, req, resp, DORMANCY_MESSAGE);
			} else if (member.status().equals(MemberStatus.UNREGISTERED)) {
				forwardToAlert(session, req, resp, UNREGISTER_MESSAGE);
			}

			req.setAttribute("memberID", String.valueOf(member.id()));
			System.out.println("memberID = "+ member.id());
		}

		filterChain.doFilter(req, resp);
	}

	private void forwardToAlert(
		final HttpSession session,
		final HttpServletRequest req,
		final HttpServletResponse resp,
		final String alertMessage
	) throws ServletException, IOException {
		String originalUrl = req.getContextPath() + "/main";
		if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute("originalUrl"))) {
			originalUrl = (String)session.getAttribute("originalUrl");
		}

		req.setAttribute("originalUrl", originalUrl);
		req.setAttribute("alert", alertMessage);

		final RequestDispatcher rd = req.getRequestDispatcher("/exception");
		rd.forward(req, resp);
	}

}
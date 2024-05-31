package com.blog.tech.global.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebFilter(filterName = "serviceFilter", urlPatterns = "/api/*")
public class AuthorizationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// todo context 정보 가져오기 - controller, service, repository 등
	}

	@Override
	public void doFilter(
		final ServletRequest servletRequest,
		final ServletResponse servletResponse,
		final FilterChain filterChain
	) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) servletRequest;
		final HttpServletResponse resp = (HttpServletResponse) servletResponse;

		final Optional<Cookie> optionalCookie = Arrays.stream(req.getCookies())
			.filter(it -> it.getName().equals("JSESSIONID"))
			.findFirst();
		if (optionalCookie.isEmpty()) {
			req.setAttribute("alert", "Invalid Access: 로그인을 해주세요.");
			forwardToAlert(req, resp);
			return;
		}

		final HttpSession session = req.getSession(false);
		if (session == null) {
			req.setAttribute("alert", "Invalid Access: 유효하지 않은 세션입니다.");
			forwardToAlert(req, resp);
			return;
		}

		final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
		if (member == null) {
			req.setAttribute("alert", "Invalid Access: 사용자 정보를 찾을 수 없습니다.");
			forwardToAlert(req, resp);
			return;
		}
		if (!member.status().equals(MemberStatus.DORMANCY)) {
			req.setAttribute("alert", "Invalid Access: 휴면해제를 해주세요." );
			forwardToAlert(req, resp);
			return;
		}
		if (!member.status().equals(MemberStatus.UNREGISTERED)) {
			req.setAttribute("alert", "Invalid Access: 탈퇴 처리를 진행중인 회원입니다.." );
			forwardToAlert(req, resp);
			return;
		}

		filterChain.doFilter(req, resp);
	}

	private void forwardToAlert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final RequestDispatcher rd = req.getRequestDispatcher("/exception/alert.jsp");
		rd.include(req, resp);
	}

}
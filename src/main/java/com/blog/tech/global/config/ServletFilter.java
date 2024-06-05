package com.blog.tech.global.config;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ServletFilter implements Filter {

	@Override
	public void doFilter(
		final ServletRequest servletRequest,
		final ServletResponse servletResponse,
		final FilterChain filterChain
	) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) servletRequest;
		final HttpServletResponse resp = (HttpServletResponse) servletResponse;

		final String requestURI = req.getRequestURI();
		if (!requestURI.startsWith("/upload/images")) {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		}

		final HttpSession session = req.getSession(false);
		String originalUrl = req.getContextPath() + "/main";
		if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute("originalUrl"))) {
			originalUrl = (String)session.getAttribute("originalUrl");
		}

		System.out.println(">>>>>>>>>>>>>>>>> " + originalUrl);
		filterChain.doFilter(req, resp);
		System.out.println("<<<<<<<<<<<<<<<<< " + req.getRequestURI());

		final HttpSession filteringSession = req.getSession(false);
		if (Objects.nonNull(filteringSession)) {
			filteringSession.setAttribute("originalUrl", requestURI);
		}
	}

}

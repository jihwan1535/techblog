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

@WebFilter(filterName = "servletFilter", urlPatterns = "/*")
public class ServletFilter implements Filter {

	@Override
	public void doFilter(
		final ServletRequest servletRequest,
		final ServletResponse servletResponse,
		final FilterChain filterChain
	) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) servletRequest;
		final HttpServletResponse resp = (HttpServletResponse) servletResponse;
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		final HttpSession session = req.getSession(false);
		String originalUrl = req.getContextPath() + "/main";
		if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute("originalUrl"))) {
			originalUrl = (String)session.getAttribute("originalUrl");
		}

		filterChain.doFilter(req, resp);

		if (Objects.nonNull(session)) {
			session.setAttribute("originalUrl", req.getRequestURI());
		}
	}

}

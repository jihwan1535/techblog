package com.blog.tech.global.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.repository.dao.MemberDao;
import com.blog.tech.domain.member.repository.dao.MemberInfoDao;
import com.blog.tech.domain.member.service.MemberService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MysqlLoaderListener implements ServletContextListener {

	private final String DB_URL = "jdbc:mysql://localhost:3306/blog?userSSL=false&userUnicode=true&allowPublicKeyRetrieval=true";
	private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String USER_NAME = "root";
	private final String PASSWORD = "1535";

	private Connection conn;

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			sce.getServletContext().setAttribute("conn", conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

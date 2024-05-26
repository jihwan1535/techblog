package com.blog.tech.global.config;

import java.sql.Connection;
import java.sql.DriverManager;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MySqlConfig implements ServletContextListener {

	private final String DB_URL = "jdbc:mysql://localhost:3306/blog?userSSL=false&userUnicode=true&allowPublicKeyRetrieval=true";
	private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String USER_NAME = "root";
	private final String PASSWORD = "1535";

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			Class.forName(DB_DRIVER);
			String url = DB_URL;
			String username = USER_NAME;
			String password = PASSWORD;

			final Connection conn = DriverManager.getConnection(url, username, password);
			sce.getServletContext().setAttribute("conn", conn);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		final Connection conn = (Connection) sce.getServletContext().getAttribute("conn");
		if (conn != null) {
			try {
				conn.close();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
}

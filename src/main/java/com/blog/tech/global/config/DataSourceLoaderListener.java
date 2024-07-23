package com.blog.tech.global.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DataSourceLoaderListener implements ServletContextListener {

	private DataSource dataSource;

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("hikari.properties"));
			HikariConfig config = new HikariConfig(properties);
			dataSource = new HikariDataSource(config);
			sce.getServletContext().setAttribute("dataSource", dataSource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		if (dataSource instanceof HikariDataSource) {
			((HikariDataSource) dataSource).close();
		}
	}

}

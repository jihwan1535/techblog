package com.blog.tech.domain.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class TransactionManager {

	private final Connection connection;

	public TransactionManager(final DataSource dataSource) {
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void begin() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void after(final BaseDaoIfs dao) {
		try {
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

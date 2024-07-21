package com.blog.tech.domain.common;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao implements BaseDaoIfs {

	protected Connection connection;

	@Override
	public void setConnection(final Connection connection) {
		this.connection = connection;
	}

	@Override
	public Connection getConnection() {
		try {
			if (connection.isClosed() || connection == null) {
				throw new IllegalArgumentException("invalid connection");
			}
			return connection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

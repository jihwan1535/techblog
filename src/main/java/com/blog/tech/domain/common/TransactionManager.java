package com.blog.tech.domain.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class TransactionManager {

	private final DataSource dataSource;

	public TransactionManager(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	public void begin(final Connection connection) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void commit(final Connection connection) {
		try {
			if (connection != null) {
				connection.commit();
				connection.setAutoCommit(true);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rollback(final Connection connection) {
		try {
			if (connection != null) {
				connection.rollback();
				connection.setAutoCommit(true);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

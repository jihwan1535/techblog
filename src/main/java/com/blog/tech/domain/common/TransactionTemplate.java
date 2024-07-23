package com.blog.tech.domain.common;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTemplate {

    private final TransactionManager transactionManager;

    public TransactionTemplate(final TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public <T> T execute(final TransactionCallback callback) {
        T result = null;
        try (final Connection conn = transactionManager.getConnection()) {
            ConnectionManager.setConnection(conn);
            transactionManager.begin(conn);
            result = (T) callback.execute();
            transactionManager.commit(conn);
        } catch (SQLException e) {
            transactionManager.rollback(ConnectionManager.getConnection());
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.removeConnection();
        }
        return result;
    }

}

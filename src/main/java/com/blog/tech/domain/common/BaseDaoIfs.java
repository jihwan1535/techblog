package com.blog.tech.domain.common;

import java.sql.Connection;

public interface BaseDaoIfs {

	void setConnection(final Connection connection);

	Connection getConnection();
}

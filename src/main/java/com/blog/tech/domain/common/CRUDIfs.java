package com.blog.tech.domain.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CRUDIfs<ENTITY> {

	ENTITY save(final ENTITY data) throws SQLException;
	ENTITY findById(final Long id);
	List<ENTITY> findByAll();
	void delete(final Long id);

}

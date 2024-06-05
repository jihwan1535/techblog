package com.blog.tech.domain.common;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDIfs<ENTITY> {

	ENTITY save(final ENTITY data) throws SQLException;
	Optional<ENTITY> findById(final Long id) throws SQLException;
	List<ENTITY> findAll() throws SQLException;
	void delete(final Long id) throws SQLException;

}

package com.blog.tech.domain.post.repository.ifs;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.Post;

public interface PostRepository extends CRUDIfs<Post> {
	List<Post> findTop10ByIdDescId(final Long id) throws SQLException;
}

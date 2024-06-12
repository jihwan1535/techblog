package com.blog.tech.domain.post.repository.ifs;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.Topic;

public interface TopicRepository extends CRUDIfs<Topic> {

	List<Topic> findAllByCategoryId(final Long categoryId) throws SQLException;

}

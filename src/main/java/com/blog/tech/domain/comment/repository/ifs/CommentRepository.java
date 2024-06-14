package com.blog.tech.domain.comment.repository.ifs;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.comment.entity.Comment;

public interface CommentRepository extends CRUDIfs<Comment> {

	List<Comment> findTop10ByPostIdAndStatusDescId(final Long postId) throws SQLException;

}

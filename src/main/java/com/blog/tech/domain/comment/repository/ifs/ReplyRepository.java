package com.blog.tech.domain.comment.repository.ifs;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.comment.entity.Reply;

public interface ReplyRepository extends CRUDIfs<Reply> {

	List<Reply> findAllByCommentIdAndStatus(final Long id, final Status status) throws SQLException;

}

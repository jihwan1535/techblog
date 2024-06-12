package com.blog.tech.domain.post.repository.ifs;

import java.sql.SQLException;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.ConnectHashtag;

public interface ConnectHashtagRepository extends CRUDIfs<ConnectHashtag> {

	Optional<ConnectHashtag> findByHashtagIdAndPostId(final Long hashtagId, final Long postId) throws SQLException;

}

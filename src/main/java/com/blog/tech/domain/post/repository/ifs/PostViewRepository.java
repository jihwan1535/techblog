package com.blog.tech.domain.post.repository.ifs;

import java.sql.SQLException;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.PostView;
import com.blog.tech.domain.post.repository.dao.PostViewDao;

public interface PostViewRepository extends CRUDIfs<PostView> {

	Optional<PostView> findByPostIdAndIP(final Long postId, final String ip) throws SQLException;

}

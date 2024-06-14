package com.blog.tech.domain.post.repository.ifs;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.Hashtag;

public interface HashtagRepository extends CRUDIfs<Hashtag> {

	Optional<Hashtag> findByName(final String hashtag) throws SQLException;

	List<Hashtag> findAllByPostId(Long postId) throws SQLException;

}

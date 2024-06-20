package com.blog.tech.domain.post.repository.ifs;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.PostText;

public interface PostRepository extends CRUDIfs<Post> {

	List<Post> findTop10ByLessThanIdDescId(final Long id) throws SQLException;
	List<Post> findTop10ByLessThanIdAndTopicIdDescId(Long postId, Long topicId) throws SQLException;
	List<Post> findTop10ByLessThanIdAndCategoryIdDescId(Long postId, Long categoryId) throws SQLException;

	PostText saveText(final PostText postText) throws SQLException;
}

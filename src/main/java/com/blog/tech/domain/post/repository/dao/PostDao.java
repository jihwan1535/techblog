package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.PostText;
import com.blog.tech.domain.post.entity.Topic;
import com.blog.tech.domain.post.repository.ifs.PostRepository;

public class PostDao implements PostRepository {

	private final Connection conn;

	public PostDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Post save(final Post data) throws SQLException {
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private void update(final Post data) throws SQLException {
		final String sql = "UPDATE post SET topic_id = ?, category_id = ?, title = ?, content = ?, comment_count = ?, "
			+ "reply_count = ?, view_count = ?, report_count = ?, scrap_count = ?, alarm = ?, status = ?, "
			+ "updated_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		setUpdatePstmt(pstmt, data);

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated " + rows + " row(s).");
	}

	private void setUpdatePstmt(final PreparedStatement pstmt, final Post data) throws SQLException {
		pstmt.setLong(1, data.getTopicId());
		pstmt.setLong(2, data.getCategoryId());
		pstmt.setString(3, data.getTile());
		pstmt.setString(4, data.getContent());
		pstmt.setInt(5, data.getCommentCount());
		pstmt.setInt(6, data.getReplyCount());
		pstmt.setInt(7, data.getViewCount());
		pstmt.setInt(8, data.getReportCount());
		pstmt.setInt(9, data.getScrapCount());
		pstmt.setBoolean(10, data.getAlarm());
		pstmt.setString(11, data.getStatus().toString());
		pstmt.setTimestamp(12, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setLong(13, data.getId());
	}

	private Post create(final Post data) throws SQLException {
		final String sql = "INSERT INTO post (id, member_info_id, topic_id, category_id, title, content, "
			+ "status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		setCreatePstmt(pstmt, data);

		final int rows = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}
		rs.close();
		pstmt.close();
		System.out.println("Inserted post " + rows + " row(s).");

		return data;
	}

	private void setCreatePstmt(final PreparedStatement pstmt, final Post data) throws SQLException {
		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getMemberInfoId());
		pstmt.setLong(3, data.getTopicId());
		pstmt.setLong(4, data.getCategoryId());
		pstmt.setString(5, data.getTile());
		pstmt.setString(6, data.getContent());
		pstmt.setString(7, data.getStatus().toString());
		pstmt.setTimestamp(8, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setTimestamp(9, Timestamp.valueOf(data.getUpdatedAt()));
	}

	@Override
	public Optional<Post> findById(final Long id) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM post p JOIN member_info m "
			+ "ON p.member_info_id = m.id JOIN topic t ON p.topic_id = t.id JOIN category c ON p.category_id = c.id "
			+ "WHERE p.id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Post post = getJoinPost(rs);
		rs.close();
		pstmt.close();

		return Optional.of(post);
	}

	@Override
	public List<Post> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public List<Post> findTop10ByLessThanIdDescId(final Long id) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM post p JOIN member_info m "
			+ "ON p.member_info_id = m.id JOIN topic t ON p.topic_id = t.id JOIN category c ON p.category_id = c.id "
			+ "WHERE p.id < ? ORDER BY p.id DESC LIMIT 10;");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		final List<Post> posts = new ArrayList<>();
		while (rs.next()) {
			posts.add(getJoinPost(rs));
		}

		rs.close();
		pstmt.close();
		return posts;
	}

	@Override
	public List<Post> findTop10ByLessThanIdAndTopicIdDescId(
		final Long postId,
		final Long topicId
	) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM post p JOIN member_info m "
			+ "ON p.member_info_id = m.id JOIN topic t ON p.topic_id = t.id JOIN category c ON p.category_id = c.id "
			+ "WHERE p.id < ? AND p.topic_id = ? ORDER BY p.id DESC LIMIT 10;");
		pstmt.setLong(1, postId);
		pstmt.setLong(2, topicId);

		final ResultSet rs = pstmt.executeQuery();

		final List<Post> posts = new ArrayList<>();
		while (rs.next()) {
			posts.add(getJoinPost(rs));
		}

		rs.close();
		pstmt.close();

		return posts;
	}

	@Override
	public List<Post> findTop10ByLessThanIdAndCategoryIdDescId(
		final Long postId,
		final Long categoryId
	) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM post p JOIN member_info m "
			+ "ON p.member_info_id = m.id JOIN topic t ON p.topic_id = t.id JOIN category c ON p.category_id = c.id "
			+ "WHERE p.id < ? AND p.category_id = ? ORDER BY p.id DESC LIMIT 10;");
		pstmt.setLong(1, postId);
		pstmt.setLong(2, categoryId);

		final ResultSet rs = pstmt.executeQuery();

		final List<Post> posts = new ArrayList<>();
		while (rs.next()) {
			posts.add(getJoinPost(rs));
		}

		rs.close();
		pstmt.close();

		return posts;
	}

	private Post getJoinPost(final ResultSet rs) throws SQLException {
		final Post post = Post.from(rs, 0);
		final MemberInfo memberInfo = MemberInfo.from(rs, 15);
		final Topic topic = Topic.from(rs, 27);
		final Category category = Category.from(rs, 30);

		post.setMemberInfo(memberInfo);
		post.setTopic(topic);
		post.setCategory(category);

		return post;
	}

	@Override
	public PostText saveText(final PostText data) throws SQLException {
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private void update(final PostText data) throws SQLException {
		final String sql = "UPDATE post_text SET content = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getContent());
		pstmt.setLong(2, data.getId());

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated post_text " + rows + " row(s).");
	}

	private PostText create(final PostText data) throws SQLException {
		final String sql = "INSERT INTO post_text (id, content) VALUES (?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, data.getId());
		pstmt.setString(2, data.getContent());

		final int rows = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}
		rs.close();
		pstmt.close();
		System.out.println("Inserted post " + rows + " row(s).");

		return data;
	}

}

package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.PostView;
import com.blog.tech.domain.post.repository.ifs.PostViewRepository;

public class PostViewDao implements PostViewRepository {

	private final Connection conn;

	public PostViewDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public PostView save(final PostView data) throws SQLException {
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private void update(final PostView data) throws SQLException {
		final String sql = "UPDATE post_view SET ip = ?, view_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getIp());
		pstmt.setDate(2, Date.valueOf(data.getViewAt()));
		pstmt.setLong(3, data.getId());

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated " + rows + " row(s).");
	}

	private PostView create(final PostView data) throws SQLException {
		final String sql = "INSERT INTO post_view (id, post_id, ip, view_at) VALUES (?, ?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getPostId());
		pstmt.setString(3, data.getIp());
		pstmt.setDate(4, Date.valueOf(data.getViewAt()));

		final int rows = pstmt.executeUpdate();
		final ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}
		rs.close();
		pstmt.close();
		System.out.println("Inserted post " + rows + " row(s).");

		return data;
	}

	@Override
	public Optional<PostView> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<PostView> findAll() throws SQLException {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public Optional<PostView> findByPostIdAndIP(final Long postId, final String ip) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM post_view v JOIN post p "
			+ "ON v.post_id = p.id WHERE v.ip = ? AND v.post_id = ?");
		pstmt.setString(1, ip);
		pstmt.setLong(2, postId);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final PostView view = PostView.from(rs, 0);
		final Post post = Post.from(rs, 4);
		view.setPost(post);

		rs.close();
		pstmt.close();

		return Optional.of(view);
	}
}

package com.blog.tech.domain.comment.repository.factory;

import com.blog.tech.domain.comment.repository.dao.CommentDao;
import com.blog.tech.domain.comment.repository.dao.ReplyDao;
import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;

public class CommentRepositoryFactory {

    private static CommentRepository commentRepository;
    private static ReplyRepository replyRepository;

    public static CommentRepository getCommentRepository() {
        if (commentRepository == null) {
            synchronized (CommentRepository.class) {
                if (commentRepository == null) {
                    commentRepository = new CommentDao();
                }
            }
        }
        return commentRepository;
    }

    public static ReplyRepository getReplyRepository() {
        if (replyRepository == null) {
            synchronized (ReplyRepository.class) {
                if (replyRepository == null) {
                    replyRepository = new ReplyDao();
                }
            }
        }
        return replyRepository;
    }

}

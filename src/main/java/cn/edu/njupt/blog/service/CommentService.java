package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Comment;

public interface CommentService {
    /**
     * 根据id获取 Comment
     * @param id
     * @return
     */
    Comment getCommentById(Long id);
    /**
     * 删除评论
     * @param id
     * @return
     */
    void removeComment(Long id);
}

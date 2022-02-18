package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Comment;
import cn.edu.njupt.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }
}

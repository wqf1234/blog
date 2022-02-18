package cn.edu.njupt.blog.repository;

import cn.edu.njupt.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

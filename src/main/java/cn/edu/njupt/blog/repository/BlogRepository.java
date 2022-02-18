package cn.edu.njupt.blog.repository;

import cn.edu.njupt.blog.domain.Catalog;
import cn.edu.njupt.blog.domain.User;
import cn.edu.njupt.blog.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface BlogRepository extends JpaRepository<EsBlog, Long> {

    /**
     * 根据用户名、博客标题分页查询博客列表
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<EsBlog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名、博客查询博客列表（时间逆序）
     * @param title
     * @param user
     * @return
     */
    Page<EsBlog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user);

    /**
     * 根据分类查询博客列表
     * @param catalog
     * @param pageable
     * @return
     */
    Page<EsBlog> findByCatalog(Catalog catalog, Pageable pageable);
}

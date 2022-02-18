package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Catalog;
import cn.edu.njupt.blog.domain.User;
import cn.edu.njupt.blog.domain.es.EsBlog;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

/**
 * Blog服务接口
 */
public interface BlogService {
    /**
     * 保存Blog
     * @param blog
     * @return
     */
    EsBlog saveBlog(EsBlog blog);

    /**
     * 删除Blog
     * @param id
     * @return
     */
    void removeBlog(Long id);

    /**
     * 更新Blog
     * @param blog
     * @return
     */
    EsBlog updateBlog(EsBlog blog);

    /**
     * 根据id获取Blog
     * @param id
     * @return
     */
    EsBlog getBlogById(Long id);

    /**
     * 根据用户名进行分页模糊查询（最新）
     * @param user
     * @return
     */
    Page<EsBlog> listBlogsByTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名进行分页模糊查询（最热）
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<EsBlog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable);

    /**
     * 根据分类进行查询
     * @param catalog
     * @param pageable
     * @return
     */
    Page<EsBlog> listBlogsByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 阅读量递增
     * @param id
     */
    void readingIncrease(Long id);

    EsBlog createVote(Long blogId);

    void removeVote(Long blogId, Long voteId);
}

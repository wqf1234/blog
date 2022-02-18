package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Catalog;
import cn.edu.njupt.blog.domain.User;
import cn.edu.njupt.blog.domain.Vote;
import cn.edu.njupt.blog.domain.es.EsBlog;
import cn.edu.njupt.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
@Service
public class BlogServiceImlp implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public EsBlog saveBlog(EsBlog blog) {
        EsBlog returnBlog = blogRepository.save(blog);
        return returnBlog;
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    @Override
    public EsBlog updateBlog(EsBlog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public EsBlog getBlogById(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Page<EsBlog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
        //模糊查询
        title = "%" + title + "%";
        String tags = title;
        Page<EsBlog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user);
        return blogs;
    }

    @Override
    public Page<EsBlog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<EsBlog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {
        EsBlog blog = blogRepository.findById(id).get();
        blog.setReadSize(blog.getComments()+1);
        this.saveBlog(blog);
    }

    @Override
    public EsBlog createVote(Long blogId) {

        EsBlog originalBlog = blogRepository.findById(blogId).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalBlog.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return blogRepository.save(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        EsBlog originalBlog = blogRepository.findById(blogId).get();
        originalBlog.removeVote(voteId);
        blogRepository.save(originalBlog);
    }

    @Override
    public Page<EsBlog> listBlogsByCatalog(Catalog catalog, Pageable pageable){
        Page<EsBlog> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }
}

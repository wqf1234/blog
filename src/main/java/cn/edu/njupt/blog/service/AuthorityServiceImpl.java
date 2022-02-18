package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Authority;
import cn.edu.njupt.blog.domain.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authority服务接口的实现
 */
@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    private AuthorityRepository authorityRepository;
    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findById(id).get();
    }
}

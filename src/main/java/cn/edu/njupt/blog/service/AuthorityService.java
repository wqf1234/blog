package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Authority;

public interface AuthorityService {
    /**
     * 根据ID查询Authority
     * @param id
     * @return
     */
    Authority getAuthorityById(Long id);
}

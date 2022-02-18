package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.print.Pageable;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 新增、编辑、保存用户
     * @param user
     * @return
     */
    User saveOrUpdateUser(User user);

    /**
     * 注册用户
     * @param user
     * @return
     */
    User registerUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void removeUser(Long id);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 根据用户名进行分页模糊查询
     * @param name
     * @param pageable
     * @return
     */
    Page<User> listUserByNameLike(String name, Pageable pageable);


    UserDetails loadUserByUserName(String username);
}

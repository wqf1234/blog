package cn.edu.njupt.blog.controller;

import cn.edu.njupt.blog.domain.Authority;
import cn.edu.njupt.blog.domain.User;
import cn.edu.njupt.blog.repository.UserRepository;
import cn.edu.njupt.blog.service.AuthorityService;
import cn.edu.njupt.blog.service.UserService;
import cn.edu.njupt.blog.util.ConstraintViolationExceptionHandler;
import cn.edu.njupt.blog.vo.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * User 控制器
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * 查询所有用户
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView list(Model model){
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("users/list", "usermodel", model);
    }

    /**
     * 根据id查询用户
     * @param id
     * @param model
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model){
        User user = userRepository.findById(id).get();   //没有findOne,findById的类型不一致
        model.addAttribute("user", user);
        model.addAttribute("title", "查看用户");
        return new ModelAndView("users/view", "usermodel", model);
    }

    /**
     * 获取创建表单页面
     * @param model
     * @return
     */
    @GetMapping("form")
    public ModelAndView createForm(Model model){
        model.addAttribute("user", new User(null, null, null, null));
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/form", "usermodel", model);
    }

    /**
     * 保存或者修改用户
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user, Long authorityId){
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId));
        user.setAuthorities(authorities);

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id){
        userRepository.deleteById(id);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 获取修改用户的界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model){
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/form", "userModel", model);
    }
}

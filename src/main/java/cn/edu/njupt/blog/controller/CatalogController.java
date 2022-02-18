package cn.edu.njupt.blog.controller;

import cn.edu.njupt.blog.domain.Catalog;
import cn.edu.njupt.blog.domain.User;
import cn.edu.njupt.blog.service.CatalogService;
import cn.edu.njupt.blog.service.UserService;
import cn.edu.njupt.blog.util.ConstraintViolationExceptionHandler;
import cn.edu.njupt.blog.vo.CatalogVo;
import cn.edu.njupt.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UserService userService;

    /**
     * 获取分类列表
     * @param username
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "username", required = true) String username, Model model) {
        User user = (User) userService.loadUserByUserName(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        //判断操作用户是否是分类的所有者
        boolean isOwner = false;

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }
        model.addAttribute("isCatalogsOwner", isOwner);
        model.addAttribute("catalogs", catalogs);
        return "/userspace/u :: #catalogRepleace";
    }

    /**
     * 创建分类
     * @param catalogVo
     * @return
     */
    @PostMapping
    @PreAuthorize("authentication.name.equals(#catalogVo.username)") //指定用户才能操作方法
    public ResponseEntity<Response> create(@RequestBody CatalogVo catalogVo){
        String username = catalogVo.getUsername();
        Catalog catalog = catalogVo.getCatalog();

        User user = (User)userService.loadUserByUserName(username);

        try{
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);

        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除分类
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> delete(String username, @PathVariable("id") Long id){
        try {
            catalogService.removeCatalog(id);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));

        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功",null));
    }

    /**
     * 获取分类编辑界面
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String getCatalogEdit(Model model){
        Catalog catalog = new Catalog((null, null));
        model.addAttribute("catalog", catalog);
        return "/userspace/catalogedit";
    }

    /**
     * 根据id获取编辑界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String getCatalogById(@PathVariable("id") Long id, Model model){
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog", catalog);
        return "/userspace/catalogedit";
    }
}

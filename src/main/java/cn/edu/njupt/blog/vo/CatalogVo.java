package cn.edu.njupt.blog.vo;


import cn.edu.njupt.blog.domain.Catalog;

import java.io.Serializable;

public class CatalogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private Catalog catalog;

    public CatalogVo(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }


}

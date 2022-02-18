package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Catalog;
import cn.edu.njupt.blog.domain.User;
import cn.edu.njupt.blog.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CatalogServiceImpl {
    @Autowired
    private CatalogRepository catalogRepository;
    @Override
    public Catalog saveCatalog(Catalog catalog) throws IllegalAccessException {
        //判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (list != null && list.size() > 0){
            throw new IllegalAccessException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id){
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog getCatalogbyId(Long id){
        return catalogRepository.findById(id).get();
    }

    @Override
    public List<Catalog> listCatalogs(User user){
        return catalogRepository.findByUser(user);
    }
}

package com.backend.AndroidApp.category;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CategoryService implements ICategoryService{

    CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public List<Category> findTop5ByShowOnHomeTrue() {
        return categoryDao.findTop5ByShowOnHomeTrue();
    }
}

package com.backend.AndroidApp.category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    public Optional<Category> findById(int id);
    public List<Category> findTop5ByShowOnHomeTrue();
}

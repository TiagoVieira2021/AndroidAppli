package com.backend.AndroidApp.dao;

import com.backend.AndroidApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
    Optional<List<Category>> findByShowHomeTrue();
}

package com.backend.AndroidApp.category;

import com.backend.AndroidApp.dao.CategoryDao;
import com.backend.AndroidApp.model.Category;
import com.backend.AndroidApp.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    private CategoryDao categoryDao;

    @Autowired
    public CategoryController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @GetMapping("/homepage")
    @JsonView(CustomJsonView.CategoryHomepage.class)
    public ResponseEntity<List<Category>> getCategories(){
        Optional<List<Category>> categories = categoryDao.findByShowHomeTrue();

        if (categories.isPresent() && !categories.get().isEmpty()) {
            return ResponseEntity.ok(categories.get());
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Optional<Category> category = categoryDao.findById(id);

        if (category.isPresent()) return ResponseEntity.ok(category.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok(null);
    }
}

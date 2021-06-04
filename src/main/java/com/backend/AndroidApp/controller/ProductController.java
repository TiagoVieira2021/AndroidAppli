package com.backend.AndroidApp.controller;

import com.backend.AndroidApp.dao.ProductDao;
import com.backend.AndroidApp.model.Product;
import com.backend.AndroidApp.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    private ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/category/{id}")
    @JsonView(CustomJsonView.CategoryPageProduct.class)
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable int id, @RequestParam(required = false, defaultValue = "0") int p){
        Pageable page = PageRequest.of(p, 6);
        List<Product> products = productDao.findAllByCategoryListIdAndOnlineTrue(id, page);

        if (!products.isEmpty()) return ResponseEntity.ok(products);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @JsonView(CustomJsonView.ProductPage.class)
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Optional<Product> product = productDao.findById(id);
        if (product.isPresent()) return ResponseEntity.ok(product.get());
        return ResponseEntity.noContent().build();
    }

}

package com.backend.AndroidApp.dao;

import com.backend.AndroidApp.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryListIdAndOnlineTrue(int id, Pageable page);
}

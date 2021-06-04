package com.backend.AndroidApp.dao;

import com.backend.AndroidApp.model.Cart;
import com.backend.AndroidApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByProductIdIsAndCustomerIdIs(int id, int id1);
}

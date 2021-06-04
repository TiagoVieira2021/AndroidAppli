package com.backend.AndroidApp.controller;

import com.backend.AndroidApp.dao.CartDao;
import com.backend.AndroidApp.dao.CustomerDao;
import com.backend.AndroidApp.model.Cart;
import com.backend.AndroidApp.model.Customer;
import com.backend.AndroidApp.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartDao cartDao;
    private CustomerDao customerDao;
    private UsersController usersController;

    @Autowired
    public CartController(CartDao cartDao, UsersController usersController, CustomerDao customerDao) {
        this.cartDao = cartDao;
        this.usersController = usersController;
        this.customerDao = customerDao;
    }

    @PutMapping("/customer/add")
    @JsonView(CustomJsonView.ProductCart.class)
    private ResponseEntity<Customer> addToCart(@RequestHeader(value = "Authorization") String authorization,
                                                 @RequestBody Cart newCart) {
        Optional<Customer> opCustomer = usersController.getUser(authorization, Customer.class);

        if (opCustomer.isPresent()) {
            Customer customer = opCustomer.get();
            Optional<Cart> opCart = cartDao.findByProductIdIsAndCustomerIdIs(
                    newCart.getProduct().getId(), customer.getId()
            );
            if (opCart.isPresent()) {
                Cart cart = opCart.get();
                cart.setQuantity(cart.getQuantity() + newCart.getQuantity());
                newCart.setId(cart.getId());
            }

            newCart.setCustomer(customer);
            cartDao.save(newCart);
            return ResponseEntity.ok(customerDao.findById(customer.getId()).get());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{id}")
    @JsonView(CustomJsonView.ProductCart.class)
    public Optional<Customer> getCart(@PathVariable int id){
        return customerDao.findById(id);
    }

    //Delete cart

    //Delete all cart

    //Delete

    //Add all cart


}

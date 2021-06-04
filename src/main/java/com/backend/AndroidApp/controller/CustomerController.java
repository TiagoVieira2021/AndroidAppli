package com.backend.AndroidApp.controller;

import com.backend.AndroidApp.dao.CustomerDao;
import com.backend.AndroidApp.model.Customer;
import com.backend.AndroidApp.model.Users;
import com.backend.AndroidApp.security.JwtUtil;
import com.backend.AndroidApp.security.UserDetailsCustom;
import com.backend.AndroidApp.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    UsersController usersController;
    CustomerDao customerDao;
    JwtUtil jwtUtil;

    @Autowired
    public CustomerController(CustomerDao customerDao, JwtUtil jwtUtil, UsersController usersController) {
        this.customerDao = customerDao;
        this.jwtUtil = jwtUtil;
        this.usersController = usersController;
    }

    @GetMapping("")
    @JsonView(CustomJsonView.CustomerProfile.class)
    private ResponseEntity<Customer> getCustomer(@RequestHeader(value = "Authorization") String authorization) {
        Optional<Customer> customer = usersController.getUser(authorization, Customer.class);

        if (customer.isPresent()) return ResponseEntity.ok(customer.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    private ResponseEntity<String> updateCustomer(@AuthenticationPrincipal Optional<UserDetails> userDetails, @RequestBody Customer customer) {

        if (userDetails.isPresent()) {
            Customer user = (Customer) userDetails.get();
            user.setFirstname(customer.getFirstname());
            user.setLastname(customer.getLastname());
            user.setLogin(customer.getLogin());
            System.out.println(user);
            customerDao.saveAndFlush(user);
            return ResponseEntity.ok("updated");
        }
        return ResponseEntity.badRequest().body("error");
    }

    @GetMapping("/delete")
    private ResponseEntity<String> deleteCustomer(Authentication principal) {
        UserDetailsCustom userDetails = (UserDetailsCustom) principal.getPrincipal();
        Users user = userDetails.getUser();


        return ResponseEntity.ok("nice");
    }


    @GetMapping("/orders/{id}")
    @JsonView(CustomJsonView.CustomerOrders.class)
    public Optional<Customer> getOrders(@PathVariable int id){
        return customerDao.findById(id);
    }


}

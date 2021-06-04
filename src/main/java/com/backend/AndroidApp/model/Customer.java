package com.backend.AndroidApp.model;

import com.backend.AndroidApp.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customer extends Users {

    @OneToMany(mappedBy = "customer")
    @JsonView(CustomJsonView.CustomerOrders.class)
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "customer")
    @JsonView(CustomJsonView.ProductCart.class)
    private List<Cart> cartList;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }
}

package com.backend.AndroidApp.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private boolean online;
    private Date created;
    private Date modified;
    private Date deleted;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Administration createdBy;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "id_product", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_category", referencedColumnName = "id")
    )
    private List<Category> categoryList;

    @OneToMany(mappedBy = "product")
    private List<OrdersProduct> ordersProductList;

    @ManyToOne
    @JoinColumn(name = "id_quantityType")
    private QuantityType quantityType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public Administration getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Administration createdBy) {
        this.createdBy = createdBy;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<OrdersProduct> getOrdersProductList() {
        return ordersProductList;
    }

    public void setOrdersProductList(List<OrdersProduct> ordersProductList) {
        this.ordersProductList = ordersProductList;
    }
}

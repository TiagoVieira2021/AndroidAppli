package com.backend.AndroidApp.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Administration extends Users{

    @OneToMany(mappedBy = "createdBy")
    private List<Product> createdProductList;

    public List<Product> getCreatedProductList() {
        return createdProductList;
    }

    public void setCreatedProductList(List<Product> createdProductList) {
        this.createdProductList = createdProductList;
    }
}

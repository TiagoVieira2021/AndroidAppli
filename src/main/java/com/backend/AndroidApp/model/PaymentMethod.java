package com.backend.AndroidApp.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PaymentMethod {

    enum Method{
        WEEKLY, MONTHLY, YEARLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('WEEKLY', 'MONTHLY', 'YEARLY')")
    private Method method;

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

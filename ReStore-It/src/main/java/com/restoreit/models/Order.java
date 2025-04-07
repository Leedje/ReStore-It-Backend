package com.restoreit.models;

import java.util.UUID;
import java.util.List;
import com.restoreit.models.Product;

//@Entity
public class Order {

    //@GeneratedValue
    private UUID id;

    //@OneToOne
    private UUID customerId; //remember that customers don't need an account

    //@ManyToMany
    private List<Product> products;
    private boolean isComplete;

    public Order(){}

    public Order(UUID id, UUID customerId, List<Product> products, boolean status){
        this.id = id;
        this.customerId = customerId;
        this.products = products;
        this.isComplete = status;
    }

    public UUID getOrderId() {
        return id;
    }

    public void setOrderId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

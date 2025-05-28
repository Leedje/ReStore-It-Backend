package com.restoreit.models;

import java.util.UUID;
import java.util.List;
import com.restoreit.models.Product;
import jakarta.persistence.*;

//@Entity
public class Order {

   // @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

   // @ManyToMany
    private List<Product> products;
    private boolean isComplete;

    public Order(){}

    public Order(UUID id, List<Product> products, boolean status){
        this.id = id;
        this.products = products;
        this.isComplete = status;
    }

    public UUID getOrderId() {
        return id;
    }

    public void setOrderId(UUID id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

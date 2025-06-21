package com.restoreit.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Nullable
    private String description;

    @Nullable
    @ManyToMany
    private List<Category> categories;

    private String size;
    private double price;
    private String seller;
    // private String image;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Product() {}

    public Product(String name, @Nullable String description, String size, double price) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.price = price;
        this.seller = this.user.getName();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}

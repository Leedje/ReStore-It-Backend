package com.restoreit.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;

    @ManyToMany
    private List<Category> categories;

    private double price;
    private int numberOfProducts;
    private String seller;
    // private List<Image> images;

    public Product() {}

    public Product(String name, String description, double price, int numberOfProducts, String seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.numberOfProducts = numberOfProducts;
        this.seller = seller;
    }

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

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

//    public List<Image> getImages() {
//        return images;
//    }
//
//    //Set images for now
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }
}

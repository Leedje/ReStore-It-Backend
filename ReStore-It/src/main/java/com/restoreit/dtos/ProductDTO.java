package com.restoreit.dtos;

import java.util.List;

public class ProductDTO {
    public int id;
    public String name;
    public String description;
    public List<CategoryDTO> categories;
    public int price;
    public int numberOfProducts;
    public String seller;
}

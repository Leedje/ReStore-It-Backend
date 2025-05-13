package com.restoreit.dtos;

import java.util.List;
import java.util.UUID;

public class ProductDTO {
    public UUID id;
    public String name;
    public String description;
    public List<CategoryDTO> categories;
    public String size;
    public int price;
    public int numberOfProducts;
    public String seller;
    public UserDTO user;
}

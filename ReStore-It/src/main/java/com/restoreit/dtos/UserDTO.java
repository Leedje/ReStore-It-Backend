package com.restoreit.dtos;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    public UUID id;
    public String name;
    public String email;
    public String password; //implement hashing
    public List<ProductDTO> products;
}

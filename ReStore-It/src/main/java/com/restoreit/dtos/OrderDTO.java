package com.restoreit.dtos;

import java.util.List;
import java.util.UUID;

public class OrderDTO {
    public UUID id;

    public List<ProductDTO> products;
    public boolean isComplete;

    public String firstName;
    public String lastName;
    public String phone;
    public String email;
    public String address;
    public String paymentMethod;
}

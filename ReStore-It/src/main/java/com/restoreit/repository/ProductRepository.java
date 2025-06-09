package com.restoreit.repository;

import com.restoreit.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    public List<Product> findByUserId(UUID id);

    Product findByIdAndUserId(UUID productId, UUID id);
}

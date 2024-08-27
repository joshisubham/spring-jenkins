package com.project.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springboot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
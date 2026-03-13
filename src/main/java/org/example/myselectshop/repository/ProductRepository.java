package org.example.myselectshop.repository;

import org.example.myselectshop.entity.Product;
import org.example.myselectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByUser(User user);
}

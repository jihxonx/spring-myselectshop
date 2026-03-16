package org.example.myselectshop.repository;

import org.example.myselectshop.dto.ProductResponseDto;
import org.example.myselectshop.entity.Product;
import org.example.myselectshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByUser(User user, Pageable pageable);

    Page<Product> findAllByUserAndProductFoldersList_FolderId(User user, Long folderId, Pageable pageable);
}

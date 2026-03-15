package org.example.myselectshop.repository;

import org.example.myselectshop.entity.Folder;
import org.example.myselectshop.entity.Product;
import org.example.myselectshop.entity.ProductFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {

    Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);
}

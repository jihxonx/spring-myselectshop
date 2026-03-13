package org.example.myselectshop.Service;

import lombok.RequiredArgsConstructor;
import org.example.myselectshop.dto.ProductRequestDto;
import org.example.myselectshop.dto.ProductResponseDto;
import org.example.myselectshop.entity.Product;
import org.example.myselectshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productRepository.save(new Product(requestDto));
        return new ProductResponseDto(product);
    }
}

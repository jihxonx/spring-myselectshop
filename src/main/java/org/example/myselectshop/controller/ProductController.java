package org.example.myselectshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.myselectshop.Service.ProductService;
import org.example.myselectshop.dto.ProductRequestDto;
import org.example.myselectshop.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }
}

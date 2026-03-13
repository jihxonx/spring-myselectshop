package org.example.myselectshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.myselectshop.Service.ProductService;
import org.example.myselectshop.dto.ProductMypriceRequestDto;
import org.example.myselectshop.dto.ProductRequestDto;
import org.example.myselectshop.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.updateProduct(id, requestDto);
    }
}

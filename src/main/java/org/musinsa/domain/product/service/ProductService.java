package org.musinsa.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.product.repository.ProductRepository;

import java.util.*;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public void reduceStock(Product product, int quantity) {
        product.reduceStock(quantity);
    }

    public Product findProductId(int productId) {
        return productRepository.findByProductId(productId);
    }

}
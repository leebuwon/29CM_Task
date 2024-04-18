package org.musinsa.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.product.exception.NotFoundProductIdException;
import org.musinsa.domain.product.repository.ProductRepository;
import org.musinsa.global.storage.ProductStorage;

import java.util.*;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * product List 역순 정렬
     */
    public List<Product> getSortedProducts() {
        return productRepository.findAll();
    }

    public void reduceStock(int productId, int quantity) {
        Product product = findProductId(productId);
        product.reduceStock(quantity);
    }

    public Product findProductId(int productId) {
        return productRepository.findProductById(productId);
    }

}
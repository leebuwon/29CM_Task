package org.musinsa.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.product.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 현재 상품의 List 출력
     */
    public List<Product> displayProducts() {
        return productService.getSortedProducts();
    }
}

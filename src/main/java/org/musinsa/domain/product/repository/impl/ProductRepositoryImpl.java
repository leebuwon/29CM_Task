package org.musinsa.domain.product.repository.impl;

import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.product.exception.NotFoundProductIdException;
import org.musinsa.domain.product.repository.ProductRepository;
import org.musinsa.global.storage.ProductStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepositoryImpl() {
        products.addAll(ProductStorage.getProducts());
        products.sort(Comparator.comparing(Product::getId).reversed());
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findProductById(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new NotFoundProductIdException("NotFoundProductIdException 발생, 잘못된 상품번호입니다."));
    }
}

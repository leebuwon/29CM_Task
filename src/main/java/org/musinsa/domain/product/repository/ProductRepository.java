package org.musinsa.domain.product.repository;

import org.musinsa.domain.product.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findByProductId(int productId);
}

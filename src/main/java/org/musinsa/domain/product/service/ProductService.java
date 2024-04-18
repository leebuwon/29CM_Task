package org.musinsa.domain.product.service;

import org.musinsa.domain.product.entity.Product;
import org.musinsa.domain.order.exception.NotFoundProductIdException;
import org.musinsa.global.storage.ProductStorage;

import java.util.*;

public class ProductService {
    private final List<Product> products;

    /**
     * product List 역순 정렬
     */
    public ProductService() {
        this.products = ProductStorage.getProducts();
        this.products.sort(Comparator.comparing(Product::getId).reversed());
    }

    public List<Product> getSortedProducts() {
        return products;
    }

    public void reduceStock(int productId, int quantity) {
        Product product = findProductId(productId);
        product.reduceStock(quantity);
    }

    public Product findProductId(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new NotFoundProductIdException("NotFoundProductIdException 발생, 잘못된 상품번호입니다."));
    }
}
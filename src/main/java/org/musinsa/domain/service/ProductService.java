package org.musinsa.domain.service;

import org.musinsa.domain.entity.Order;
import org.musinsa.domain.entity.Product;

import java.util.*;

public class ProductService {
    private final Product[] products;

    /**
     * product List 역순 정렬
     */
    public ProductService() {
        this.products = Product.values();
        Arrays.sort(this.products, Comparator.comparing(Product::getId).reversed());
    }

    public Product[] getSortedProducts() {
        return products;
    }

    public void reduceStock(int productId, int quantity) {
        Product product = getProductById(productId);
        product.reduceStock(quantity);
    }

    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        throw new IllegalArgumentException("잘못된 상품번호입니다.");
    }
}
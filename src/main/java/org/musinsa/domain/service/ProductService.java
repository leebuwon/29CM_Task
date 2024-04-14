package org.musinsa.domain.service;

import org.musinsa.domain.entity.Product;
import org.musinsa.domain.exception.NotFoundProductIdException;

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
        Product product = findProductId(productId);
        product.reduceStock(quantity);
    }

    public Product findProductId(int productId) {
        return Arrays.stream(products)
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new NotFoundProductIdException("NotFoundProductIdException 발생, 잘못된 상품번호입니다."));
    }
}
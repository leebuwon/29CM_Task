package org.musinsa.domain.service;

import org.musinsa.domain.entity.Product;

import java.util.Arrays;
import java.util.Comparator;

public class ProductService {
    private final Product[] products;

    public ProductService() {
        this.products = Product.values();
        Arrays.sort(this.products, Comparator.comparing(Product::getId).reversed());
    }

    public void displayProducts() {
        System.out.println(String.format("%-8s %-28s %-7s %s", "상품번호", "상품명", "판매가격", "재고수"));
        for (Product product : products) {
            System.out.println(String.format("%-10d %-30s %-12d %d", product.getId(), product.getName(), product.getPrice(), product.getStock()));
        }
    }
}

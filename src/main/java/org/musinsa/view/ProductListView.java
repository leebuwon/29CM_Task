package org.musinsa.view;

import org.musinsa.domain.entity.Product;

public class ProductListView {
    public void displayProducts(Product[] products) {
        System.out.printf("%-8s %-28s %-7s %s%n", "상품번호", "상품명", "판매가격", "재고수");
        for (Product product : products) {
            System.out.printf("%-10d %-30s %-12d %d%n", product.getId(), product.getName(), product.getPrice(), product.getStock());
        }
        System.out.print("\n");
    }
}

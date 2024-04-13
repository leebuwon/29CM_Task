package org.musinsa.view;

import org.musinsa.domain.entity.Product;

public class ProductListView {
    public void displayProducts(Product[] products) {
        System.out.println(String.format("%-8s %-28s %-7s %s", "상품번호", "상품명", "판매가격", "재고수"));
        for (Product product : products) {
            System.out.println(String.format("%-10d %-30s %-12d %d", product.getId(), product.getName(), product.getPrice(), product.getStock()));
        }
        System.out.print("\n");
    }
}

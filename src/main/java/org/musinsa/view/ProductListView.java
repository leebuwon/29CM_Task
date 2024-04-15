package org.musinsa.view;

import org.musinsa.domain.entity.Product;

public class ProductListView {
    private static final String HEADER_FORMAT = "%-8s %-28s %-7s %s%n";
    private static final String PRODUCT_FORMAT = "%-10d %-30s %-12d %d%n";

    public void displayProducts(Product[] products) {
        displayHeader();
        for (Product product : products) {
            displayProductDetails(product);
        }
        System.out.print("\n");
    }

    private void displayHeader() {
        System.out.printf(HEADER_FORMAT, "상품번호", "상품명", "판매가격", "재고수");
    }

    private void displayProductDetails(Product product) {
        System.out.printf(PRODUCT_FORMAT, product.getId(), product.getName(), product.getPrice(), product.getStock());
    }
}

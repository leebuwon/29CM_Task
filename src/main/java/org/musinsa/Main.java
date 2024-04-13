package org.musinsa;


import org.musinsa.domain.controller.ProductController;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;
import org.musinsa.view.ProductListView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        ProductListView productListView = new ProductListView();
        ProductController productController = new ProductController(productService, orderService, productListView);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "o":
                    productController.displayProducts();
                    productController.processOrder(scanner);
                    break;
                case "q":
                case "quit":
                    System.out.println("고객님의 주문 감사합니다.");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }
}

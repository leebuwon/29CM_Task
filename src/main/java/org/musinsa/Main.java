package org.musinsa;


import org.musinsa.domain.controller.ProductController;
import org.musinsa.domain.entity.Product;
import org.musinsa.domain.service.OrderService;
import org.musinsa.domain.service.ProductService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        ProductController productController = new ProductController(productService, orderService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("o")) {
                productController.displayProducts();
                productController.processOrder(scanner);
            } else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {  // 종료 옵션
                System.out.println("고객님의 주문 감사합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }

        scanner.close();
    }
}

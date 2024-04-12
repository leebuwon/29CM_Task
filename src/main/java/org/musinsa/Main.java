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
        ProductService productService = new ProductService();  // ProductService 인스턴스 생성
        OrderService orderService = new OrderService();
        ProductController productController = new ProductController(productService, orderService);  // 생성자에 주입

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
            String input = scanner.nextLine().trim();  // 사용자 입력 받기

            if (input.equalsIgnoreCase("o")) {  // 주문 옵션
                productController.displayProducts();
                productController.processOrder(scanner);  // Scanner를 인자로 넘김
            } else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {  // 종료 옵션
                System.out.println("고객님의 주문 감사합니다.");
                break;  // 프로그램 종료 전에 반복문을 빠져나옴
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");  // 잘못된 입력 처리
            }
        }

        scanner.close();  // Scanner는 반복문 종료 후 단 한 번만 닫힘
    }
}

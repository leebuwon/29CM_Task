package org.musinsa;


import org.musinsa.domain.entity.Product;
import org.musinsa.domain.service.ProductService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductService productService = new ProductService();

        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
            String input = scanner.nextLine();
            switch (input) {
                case "o":
                    productService.displayProducts();
                    break;
                case "q":
                    System.out.println("고객님의 주문 감사합니다.");
                    scanner.close();
                    return;
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

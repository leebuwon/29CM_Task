package org.musinsa.domain.util;

import java.util.Scanner;

public class Console {
    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput(String message) {
        System.out.print(message);
        return scanner.nextLine().trim().toLowerCase();
    }

    public void close() {
        scanner.close();
    }
}

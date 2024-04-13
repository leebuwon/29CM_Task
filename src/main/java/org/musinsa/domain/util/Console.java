package org.musinsa.domain.util;

import java.util.Scanner;

public class Console {
    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim().toLowerCase();
    }

    public void close() {
        scanner.close();
    }
}

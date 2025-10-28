package org.example.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class BaseMenu {

    protected static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    protected static <T> T validateInput(Scanner scanner, String prompt, Class<T> expectedType) {
        while (true) {
            try {
                System.out.println(prompt);
                if (expectedType == Integer.class) {
                    int value = scanner.nextInt();
                    scanner.nextLine();
                    return expectedType.cast(value);
                } else if (expectedType == Double.class) {
                    double value = scanner.nextDouble();
                    validateDoubleInput(value);
                    scanner.nextLine();
                    return expectedType.cast(value);
                } else if (expectedType == String.class) {
                    String value = scanner.nextLine();
                    scanner.nextLine();
                    return expectedType.cast(value);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again");
                scanner.nextLine();
            }
        }
    }

    protected static int validateIntInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value <= max || value >= min) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again");
                scanner.nextLine();
            }

        }
    }

    private static double validateDoubleInput(double value) {
        if (value <= 0) {
            throw new InputMismatchException();
        }
        return value;
    }
}

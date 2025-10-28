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

    protected static int validateInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value <= max && value >= min) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again");
                scanner.nextLine();
            }
        }
    }

    protected static double validateInput(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            try {
                System.out.println(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value <= max && value >= min) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again");
                scanner.nextLine();
            }
        }
    }

    protected static String validateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine();
            if (value.isBlank()) {
                System.out.println("Invalid input. Please try again");
            } else {
                return value;
            }
        }
    }
}

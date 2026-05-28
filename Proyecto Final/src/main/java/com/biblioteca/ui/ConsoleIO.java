package com.biblioteca.ui;

import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner;

    public ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    public int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Ingrese un numero entero.");
            }
        }
    }

    public long leerLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            try {
                return Long.parseLong(linea);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Ingrese un numero valido.");
            }
        }
    }

    public String leerCadenaNoVacia(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("  [!] El campo no puede estar vacio.");
        }
    }

    public String leerCadena(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int leerOpcion(String prompt, int min, int max) {
        while (true) {
            int op = leerEntero(prompt);
            if (op >= min && op <= max) {
                return op;
            }
            System.out.println("  [!] Opcion fuera de rango [" + min + "-" + max + "].");
        }
    }

    public static void println(String msg) {
        System.out.println(msg);
    }
}

package com.harshvardhan.leetorganizer;

public final class Console {

    private static final String DIVIDER = "=========================================";

    private Console() {
        // Utility class
    }

    public static void printBanner() {
        System.out.println(DIVIDER);
        System.out.println("         LeetOrganizer v1.0");
        System.out.println(DIVIDER);
    }

    public static void printSection(String message) {
        System.out.println();
        System.out.println(message);
    }

    public static void printInfo(String message) {
        System.out.println(message);
    }

    public static void printSuccess(String message) {
        System.out.println(message);
    }

    public static void printWarning(String message) {
        System.out.println("⚠ " + message);
    }

    public static void printError(String message) {
        System.err.println(message);
    }

}
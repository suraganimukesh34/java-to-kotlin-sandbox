package com.example.inventory;

public class InsufficientStockException extends Exception {
    private final int shortBy;

    public InsufficientStockException(String message, int shortBy) {
        super(message);
        this.shortBy = shortBy;
    }

    public int getShortBy() {
        return shortBy;
    }
}

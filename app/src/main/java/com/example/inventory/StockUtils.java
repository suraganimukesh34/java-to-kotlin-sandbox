package com.example.inventory;

public final class StockUtils {
    public static final int LOW_STOCK_THRESHOLD = 5;

    private StockUtils() {
    }

    public static boolean isLow(Item item) {
        return item.getQuantity() <= LOW_STOCK_THRESHOLD;
    }

    public static String label(Item item) {
        return item.getName() + " [" + item.getCategory().getCode() + "]";
    }
}

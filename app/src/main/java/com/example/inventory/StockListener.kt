package com.example.inventory;

public interface StockListener {
    void onLowStock(Item item);

    void onOutOfStock(Item item);
}

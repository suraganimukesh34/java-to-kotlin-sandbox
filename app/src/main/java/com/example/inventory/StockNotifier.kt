package com.example.inventory;

import java.util.ArrayList;
import java.util.List;

public class StockNotifier {
    private final List<String> messages = new ArrayList<>();

    public StockListener asListener() {
        return new StockListener() {
            @Override
            public void onLowStock(Item item) {
                messages.add("low:" + item.getId());
            }

            @Override
            public void onOutOfStock(Item item) {
                messages.add("out:" + item.getId());
            }
        };
    }

    public List<String> getMessages() {
        return messages;
    }
}

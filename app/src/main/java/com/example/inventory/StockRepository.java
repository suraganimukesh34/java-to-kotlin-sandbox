package com.example.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StockRepository {
    private final List<Item> items = new ArrayList<>();
    private final Map<Category, Integer> totals = new HashMap<>();

    public void add(Item item) {
        if (item != null) {
            items.add(item);
            Integer current = totals.get(item.getCategory());
            totals.put(item.getCategory(), current == null ? item.getQuantity() : current + item.getQuantity());
        }
    }

    public Item findById(String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> findByCategory(Category category) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory() == category) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Item> lowStock(int threshold) {
        return items.stream().filter(item -> item.getQuantity() <= threshold).collect(Collectors.toList());
    }

    public int total(Category category) {
        Integer total = totals.get(category);
        return total == null ? 0 : total;
    }

    public void replace(Item updated) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updated.getId())) {
                items.set(i, updated);
                return;
            }
        }
    }
}

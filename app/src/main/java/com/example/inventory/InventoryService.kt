package com.example.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private final StockRepository repository;
    private final List<StockListener> listeners = new ArrayList<>();

    public InventoryService(StockRepository repository) {
        this.repository = repository;
    }

    public void addListener(StockListener listener) {
        listeners.add(listener);
    }

    public synchronized Item remove(String id, int amount) throws InsufficientStockException {
        Item item = repository.findById(id);
        if (item == null) {
            throw new IllegalArgumentException("Unknown item: " + id);
        }
        if (item.getQuantity() < amount) {
            throw new InsufficientStockException("Not enough " + item.getName(), amount - item.getQuantity());
        }
        Item updated = item.withQuantity(item.getQuantity() - amount);
        repository.replace(updated);
        if (updated.getQuantity() == 0) {
            for (StockListener listener : listeners) {
                listener.onOutOfStock(updated);
            }
        } else if (StockUtils.isLow(updated)) {
            for (StockListener listener : listeners) {
                listener.onLowStock(updated);
            }
        }
        return updated;
    }
}

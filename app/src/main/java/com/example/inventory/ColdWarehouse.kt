package com.example.inventory;

public class ColdWarehouse extends Warehouse {
    public ColdWarehouse(StockRepository repository) {
        super("cold", repository);
    }

    @Override
    public boolean canStore(Item item) {
        return item.getCategory().isPerishable();
    }
}

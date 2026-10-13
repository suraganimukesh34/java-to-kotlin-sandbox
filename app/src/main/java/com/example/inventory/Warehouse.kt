package com.example.inventory;

public abstract class Warehouse {
    protected final StockRepository repository;
    private final String name;

    protected Warehouse(String name, StockRepository repository) {
        this.name = name;
        this.repository = repository;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canStore(Item item);

    public final boolean store(Item item) {
        if (item != null && canStore(item)) {
            repository.add(item);
            return true;
        }
        return false;
    }

    public int count() {
        int count = 0;
        for (Category category : Category.values()) {
            count += repository.total(category);
        }
        return count;
    }
}

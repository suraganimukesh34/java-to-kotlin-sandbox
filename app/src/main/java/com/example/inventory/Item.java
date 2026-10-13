package com.example.inventory;

public class Item {
    private final String id;
    private final String name;
    private final Category category;
    private final int quantity;

    public Item(String id, String name, Category category, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item withQuantity(int quantity) {
        return new Item(id, name, category, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Item(" + id + " x" + quantity + ")";
    }
}

package com.example.inventory;

public enum Category {
    FOOD("F", true),
    TOOLS("T", false),
    CLOTHES("C", false);

    private final String code;
    private final boolean perishable;

    Category(String code, boolean perishable) {
        this.code = code;
        this.perishable = perishable;
    }

    public String getCode() {
        return code;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public static Category fromCode(String code) {
        for (Category category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }
        return null;
    }
}

package com.example.billing;

import java.math.BigDecimal;
import java.util.Objects;

public class Invoice {
    private final String id;
    private final String customerId;
    private final BigDecimal amount;
    private final boolean paid;

    public Invoice(String id, String customerId, BigDecimal amount, boolean paid) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.paid = paid;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public Invoice markPaid() {
        return new Invoice(id, customerId, amount, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice other = (Invoice) o;
        return paid == other.paid
                && Objects.equals(id, other.id)
                && Objects.equals(customerId, other.customerId)
                && Objects.equals(amount, other.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, amount, paid);
    }

    @Override
    public String toString() {
        return "Invoice{id='" + id + "', amount=" + amount + "}";
    }
}

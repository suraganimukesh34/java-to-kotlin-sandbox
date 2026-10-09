package com.example.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRepository {
    public interface Callback {
        void onResult(Invoice invoice);
    }

    private final List<Invoice> invoices = new ArrayList<>();
    private final Map<String, Integer> countsByCustomer = new HashMap<>();

    public static InvoiceRepository withSample() {
        InvoiceRepository repository = new InvoiceRepository();
        repository.add(new Invoice("inv-1", "cust-1", new BigDecimal("120.00"), false));
        repository.add(new Invoice("inv-2", "cust-1", new BigDecimal("75.50"), true));
        return repository;
    }

    public void add(Invoice invoice) {
        if (invoice != null) {
            invoices.add(invoice);
            Integer current = countsByCustomer.get(invoice.getCustomerId());
            countsByCustomer.put(invoice.getCustomerId(), current == null ? 1 : current + 1);
        }
    }

    public Invoice findById(String id) {
        for (Invoice invoice : invoices) {
            if (invoice.getId().equals(id)) {
                return invoice;
            }
        }
        return null;
    }

    public List<Invoice> findByCustomer(String customerId) {
        List<Invoice> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (invoice.getCustomerId().equals(customerId)) {
                result.add(invoice);
            }
        }
        return result;
    }

    public int countUnpaid() {
        int count = 0;
        for (Invoice invoice : invoices) {
            if (!invoice.isPaid()) {
                count++;
            }
        }
        return count;
    }

    public void load(final String id, final Callback callback) {
        callback.onResult(findById(id));
    }
}

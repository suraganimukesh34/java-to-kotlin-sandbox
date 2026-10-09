package com.example.billing;

public class InvoiceViewModel {
    private final InvoiceRepository repository;
    private String statusText;
    private int loadCount;

    public InvoiceViewModel(InvoiceRepository repository) {
        this.repository = repository;
    }

    public String getStatusText() {
        return statusText;
    }

    public synchronized int getLoadCount() {
        return loadCount;
    }

    public void loadInvoice(String id) {
        loadCount++;
        repository.load(id, new InvoiceRepository.Callback() {
            @Override
            public void onResult(Invoice invoice) {
                statusText = invoice != null ? describe(invoice) : "Invoice not found";
            }
        });
    }

    private String describe(Invoice invoice) {
        StringBuilder builder = new StringBuilder();
        builder.append("Invoice ").append(invoice.getId());
        builder.append(invoice.isPaid() ? " (paid)" : " (due: " + invoice.getAmount() + ")");
        return builder.toString();
    }
}

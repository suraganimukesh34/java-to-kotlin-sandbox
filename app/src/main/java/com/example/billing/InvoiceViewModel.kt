package com.example.billing

class InvoiceViewModel(private val repository: InvoiceRepository) {
    var statusText: String? = null
        private set

    var loadCount: Int = 0
        @Synchronized get
        private set

    fun loadInvoice(id: String) {
        loadCount++
        repository.load(id) { invoice ->
            statusText = if (invoice != null) describe(invoice) else "Invoice not found"
        }
    }

    private fun describe(invoice: Invoice): String {
        val builder = StringBuilder()
        builder.append("Invoice ").append(invoice.id)
        builder.append(if (invoice.isPaid) " (paid)" else " (due: " + invoice.amount + ")")
        return builder.toString()
    }
}

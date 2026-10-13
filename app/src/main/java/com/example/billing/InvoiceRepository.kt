package com.example.billing

import java.math.BigDecimal

class InvoiceRepository {
    fun interface Callback {
        fun onResult(invoice: Invoice?)
    }

    private val invoices = mutableListOf<Invoice>()
    private val countsByCustomer = mutableMapOf<String, Int>()

    fun add(invoice: Invoice?) {
        if (invoice != null) {
            invoices.add(invoice)
            val current = countsByCustomer[invoice.customerId]
            countsByCustomer[invoice.customerId] = if (current == null) 1 else current + 1
        }
    }

    fun findById(id: String): Invoice? = invoices.firstOrNull { it.id == id }

    fun findByCustomer(customerId: String): List<Invoice> =
        invoices.filter { it.customerId == customerId }

    fun countUnpaid(): Int = invoices.count { !it.isPaid }

    fun load(id: String, callback: Callback) {
        callback.onResult(findById(id))
    }

    companion object {
        @JvmStatic
        fun withSample(): InvoiceRepository {
            val repository = InvoiceRepository()
            repository.add(Invoice("inv-1", "cust-1", BigDecimal("120.00"), false))
            repository.add(Invoice("inv-2", "cust-1", BigDecimal("75.50"), true))
            return repository
        }
    }
}

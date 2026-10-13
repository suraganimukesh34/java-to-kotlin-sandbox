package com.example.billing

import java.math.BigDecimal

data class Invoice(
    val id: String,
    val customerId: String,
    val amount: BigDecimal,
    val isPaid: Boolean,
) {
    fun markPaid(): Invoice = Invoice(id, customerId, amount, true)

    override fun toString(): String = "Invoice{id='$id', amount=$amount}"
}

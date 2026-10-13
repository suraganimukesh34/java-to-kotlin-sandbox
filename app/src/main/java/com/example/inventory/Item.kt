package com.example.inventory

class Item(
    val id: String,
    val name: String,
    val category: Category,
    val quantity: Int,
) {
    fun withQuantity(quantity: Int): Item = Item(id, name, category, quantity)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Item) {
            return false
        }
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "Item($id x$quantity)"
}
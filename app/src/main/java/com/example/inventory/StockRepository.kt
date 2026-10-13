package com.example.inventory

class StockRepository {
    private val items = mutableListOf<Item>()
    private val totals = mutableMapOf<Category, Int>()

    fun add(item: Item?) {
        if (item != null) {
            items.add(item)
            totals[item.category] = (totals[item.category] ?: 0) + item.quantity
        }
    }

    fun findById(id: String): Item? = items.firstOrNull { it.id == id }

    fun findByCategory(category: Category): List<Item> = items.filter { it.category == category }

    fun lowStock(threshold: Int): List<Item> = items.filter { it.quantity <= threshold }

    fun total(category: Category): Int = totals[category] ?: 0

    fun replace(updated: Item) {
        for (i in items.indices) {
            if (items[i].id == updated.id) {
                items[i] = updated
                return
            }
        }
    }
}
package com.example.inventory

class InventoryService(private val repository: StockRepository) {
    private val listeners = mutableListOf<StockListener>()

    fun addListener(listener: StockListener) {
        listeners.add(listener)
    }

    @Synchronized
    @Throws(InsufficientStockException::class)
    fun remove(id: String, amount: Int): Item {
        val item = repository.findById(id) ?: throw IllegalArgumentException("Unknown item: $id")
        if (item.quantity < amount) {
            throw InsufficientStockException("Not enough ${item.name}", amount - item.quantity)
        }
        val updated = item.withQuantity(item.quantity - amount)
        repository.replace(updated)
        if (updated.quantity == 0) {
            listeners.forEach { it.onOutOfStock(updated) }
        } else if (StockUtils.isLow(updated)) {
            listeners.forEach { it.onLowStock(updated) }
        }
        return updated
    }
}

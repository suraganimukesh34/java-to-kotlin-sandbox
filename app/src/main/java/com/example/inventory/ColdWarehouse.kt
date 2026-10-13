package com.example.inventory

class ColdWarehouse(repository: StockRepository) : Warehouse("cold", repository) {
    override fun canStore(item: Item): Boolean = item.category.isPerishable
}

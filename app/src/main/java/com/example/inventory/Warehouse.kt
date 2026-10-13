package com.example.inventory

abstract class Warehouse protected constructor(
    val name: String,
    @JvmField protected val repository: StockRepository,
) {
    abstract fun canStore(item: Item): Boolean

    fun store(item: Item?): Boolean {
        if (item != null && canStore(item)) {
            repository.add(item)
            return true
        }
        return false
    }

    open fun count(): Int = Category.values().sumOf { repository.total(it) }
}

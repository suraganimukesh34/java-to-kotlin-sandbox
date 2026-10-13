package com.example.inventory

object StockUtils {
    const val LOW_STOCK_THRESHOLD: Int = 5

    @JvmStatic
    fun isLow(item: Item): Boolean = item.quantity <= LOW_STOCK_THRESHOLD

    @JvmStatic
    fun label(item: Item): String = item.name + " [" + item.category.code + "]"
}
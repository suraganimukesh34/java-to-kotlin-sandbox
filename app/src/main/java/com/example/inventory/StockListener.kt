package com.example.inventory

interface StockListener {
    fun onLowStock(item: Item)

    fun onOutOfStock(item: Item)
}
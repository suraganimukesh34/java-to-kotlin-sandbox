package com.example.inventory

class StockNotifier {
    val messages: MutableList<String> = mutableListOf()

    fun asListener(): StockListener = object : StockListener {
        override fun onLowStock(item: Item) {
            messages.add("low:" + item.id)
        }

        override fun onOutOfStock(item: Item) {
            messages.add("out:" + item.id)
        }
    }
}
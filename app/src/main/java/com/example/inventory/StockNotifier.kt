package com.example.inventory

class StockNotifier {
    private val _messages = mutableListOf<String>()

    val messages: List<String>
        get() = _messages

    fun asListener(): StockListener = object : StockListener {
        override fun onLowStock(item: Item) {
            _messages.add("low:${item.id}")
        }

        override fun onOutOfStock(item: Item) {
            _messages.add("out:${item.id}")
        }
    }
}

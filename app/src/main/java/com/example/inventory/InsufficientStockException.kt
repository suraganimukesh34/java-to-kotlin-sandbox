package com.example.inventory

class InsufficientStockException(message: String, val shortBy: Int) : Exception(message)
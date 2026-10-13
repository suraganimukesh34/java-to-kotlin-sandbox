package com.example.inventory

enum class Category(val code: String, val isPerishable: Boolean) {
    FOOD("F", true),
    TOOLS("T", false),
    CLOTHES("C", false);

    companion object {
        @JvmStatic
        fun fromCode(code: String?): Category? = values().firstOrNull { it.code == code }
    }
}

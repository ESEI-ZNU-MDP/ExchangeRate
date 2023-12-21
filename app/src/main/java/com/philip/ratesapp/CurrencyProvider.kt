package com.philip.ratesapp

class CurrencyProvider {
    companion object {
        val usd: Currency = Currency(id = "USD", symbol = "\uD83C\uDDFA\uD83C\uDDF8", displayName = "United States Dollar")
        val uah: Currency = Currency(id = "UAH", symbol = "\uD83C\uDDFA\uD83C\uDDE6", displayName = "Ukrainian Hryvnia")
        val eur: Currency = Currency(id = "EUR", symbol = "\uD83C\uDDEA\uD83C\uDDFA", displayName = "Euro")

        fun getCurrencyById(id: String): Currency? {
            when (id) {
                "USD" -> return usd
                "UAH" -> return uah
                "EUR" -> return eur
            }
            return null
        }

        val all: ArrayList<Currency> = arrayListOf(usd, uah, eur)
    }
}
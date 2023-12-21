package com.philip.ratesapp

class CurrencyService {
    companion object {
        private val usd = "USD"
        private val eur = "EUR"
        private val uah = "UAH"

        fun convertRate(amount: Double, fromCurrency: Currency, toCurrency: Currency): Double? {
            var coefficient = getCoefficient(fromCurrency, toCurrency)
            if (coefficient == null) {
                return null
            }

            return amount * coefficient
        }

        private fun getCoefficient(fromCurrency: Currency, toCurrency: Currency): Double? {
            when (fromCurrency.id) {
                usd -> when (toCurrency.id) {
                    usd -> return 1.0
                    eur -> return 0.92
                    uah -> return 37.21
                }
                eur -> when (toCurrency.id) {
                    usd -> return 1.09
                    eur -> return 1.0
                    uah -> return 40.61
                }
                uah -> when (toCurrency.id) {
                    usd -> return 0.027
                    eur -> return 0.025
                    uah -> return 1.0
                }
            }
            return null
        }
    }
}
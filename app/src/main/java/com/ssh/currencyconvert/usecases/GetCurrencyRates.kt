package com.ssh.currencyconvert.usecases

import com.ssh.currencyconvert.repositories.CurrencyLayerRepository
import kotlin.math.roundToInt

class GetCurrencyRates constructor(
    private val repository: CurrencyLayerRepository
) {
    private var responseValue: ResponseValue? = null

    fun execute(): ResponseValue {
        responseValue = ResponseValue(repository.getLiveExchangeRate().blockingGet().quotes)
        return exchangeRates()
    }

    fun exchangeRates(
        currency: String = "USDUSD",
        amount: Double = 1.0
    ): ResponseValue {
        responseValue ?: return ResponseValue(mutableMapOf())
        val exchangeRates = mutableMapOf<String, Double>()
        responseValue!!.let { response ->

            val usdToCurrencyRate = response.rates[currency]
            checkNotNull(usdToCurrencyRate).also {
                val conversionRate = amount / it
                response.rates.forEach { entry ->
                    val exchangeAmount: Double =
                        if (entry.key == currency) amount
                        else (conversionRate * entry.value).roundToInt().toDouble()
                    exchangeRates[entry.key] = exchangeAmount
                }
            }
        }
        return ResponseValue(exchangeRates)
    }

    data class ResponseValue(val rates: Map<String, Double>)
}
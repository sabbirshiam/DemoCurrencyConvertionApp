package com.ssh.currencyconvert.usecases

import com.ssh.currencyconvert.repositories.CurrencyLayerRepository
import io.reactivex.Single

class GetCurrencyRates constructor(
    private val repository: CurrencyLayerRepository
) {
    private var responseValue: Single<ResponseValue>? = null

    fun execute(): Single<ResponseValue> {
        responseValue = repository.getLiveExchangeRate().map {
            ResponseValue(it.quotes.toMutableMap())
        }
        return responseValue as Single<ResponseValue>
    }

    data class ResponseValue(val rates: Map<String, Double>)
}
package com.ssh.currencyconvert.usecases

import com.ssh.currencyconvert.repositories.CurrencyLayerRepository
import io.reactivex.Single

class GetCurrencies constructor(
    private val repository: CurrencyLayerRepository
) {
    private var responseValue: Single<ResponseValue>? = null

    fun execute(): Single<ResponseValue> {
        return responseValue ?: run {
            responseValue =
                repository.getSupportedCurrencyList().map { ResponseValue(it.currencies) }
            return responseValue as Single<ResponseValue>
        }
    }

    data class ResponseValue(val currencies: Map<String, String>)
}
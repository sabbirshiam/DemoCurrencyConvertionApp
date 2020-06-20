package com.ssh.currencyconvert.repositories

import com.ssh.currencyconvert.BuildConfig
import com.ssh.currencyconvert.models.Currencies
import com.ssh.currencyconvert.models.CurrencyRateQuotes
import io.reactivex.Single

interface CurrencyLayerRepository {
    fun getSupportedCurrencyList(): Single<Currencies>
    fun getLiveExchangeRate(): Single<CurrencyRateQuotes>
}

class CurrencyLayerRepositoryImpl(private val api: CurrencyLayerApi) : CurrencyLayerRepository {

    /**
     * getSupportedCurrencyList() return list of public feed items from
     * http://api.currencylayer.com/list? access_key = YOUR_ACCESS_KEY
     * https is not supported in free usage plan of currency layer.
     */
    override fun getSupportedCurrencyList(): Single<Currencies> {
        return api.getSupportedCurrencies(BuildConfig.CURRENCY_API_KEY)
    }

    /**
     * getLiveExchangeRate() return list of countries exchange rates against USD.
     * http://api.currencylayer.com/live? access_key = YOUR_ACCESS_KEY
     * https is not supported in free usage plan of currency layer.
     */
    override fun getLiveExchangeRate(): Single<CurrencyRateQuotes> {
        return api.getLiveExchangeRate(BuildConfig.CURRENCY_API_KEY)
    }
}
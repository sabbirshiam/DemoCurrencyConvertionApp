package com.ssh.currencyconvert.repositories

import com.ssh.currencyconvert.models.Currencies
import com.ssh.currencyconvert.models.CurrencyRateQuotes
import com.ssh.currencyconvert.network.RetrofitClient.API_BASE_URL
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerApi {
    /*
     * getSupportedCurrencies(access_key)
     *
     * parameter: access_key
     * response: json of list of supported currencies.
     */

    @GET("list")
    fun getSupportedCurrencies(
        @Query("access_key") format: String
    ): Single<Currencies>

    @GET("live")
    fun getLiveExchangeRate(
        @Query("access_key") format: String
    ): Single<CurrencyRateQuotes>
}
package com.ssh.currencyconvert

import com.ssh.currencyconvert.network.RetrofitClient
import com.ssh.currencyconvert.repositories.CurrencyLayerRepository
import com.ssh.currencyconvert.repositories.CurrencyLayerRepositoryImpl
import com.ssh.currencyconvert.usecases.GetCurrencies
import com.ssh.currencyconvert.usecases.GetCurrencyRates

/**
 * Enable Dependency Injection without Dagger.
 */
object Injection {

    fun provideCurrencyLayerRepository(): CurrencyLayerRepository {
        return CurrencyLayerRepositoryImpl(RetrofitClient.currencyLayerService)
    }

    fun provideGetCurrencies(): GetCurrencies {
        return GetCurrencies(provideCurrencyLayerRepository())
    }

    fun provideGetCurrencyRates(): GetCurrencyRates {
        return GetCurrencyRates(provideCurrencyLayerRepository())
    }
}
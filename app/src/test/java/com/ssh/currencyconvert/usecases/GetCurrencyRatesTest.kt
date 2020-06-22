package com.ssh.currencyconvert.usecases

import com.ssh.currencyconvert.models.Currencies
import com.ssh.currencyconvert.models.CurrencyRateQuotes
import com.ssh.currencyconvert.repositories.CurrencyLayerRepository
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class GetCurrencyRatesTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var currencyLayerRepository: CurrencyLayerRepository

    lateinit var getCurrencyRates: GetCurrencyRates


    @Before
    fun init() {
        getCurrencyRates = GetCurrencyRates(currencyLayerRepository)
    }

    @Test
    fun testExecute() {
        val rates = mapOf("USDUSD" to 0.11)
        val currencies =
            mutableMapOf("USD" to "United states of America", "AED" to "United Arab emirates")
        val currencyRateResponse = CurrencyRateQuotes(
            privacy = "",
            quotes = rates,
            source = "",
            success = true,
            terms = "",
            timestamp = 0
        )

        val currencyResponse = Currencies(
            currencies = currencies,
            privacy = "",
            success = true,
            terms = ""
        )
        val expected = GetCurrencyRates.ResponseValue(
            currencyRateResponse.quotes.toMutableMap()
        )

        Mockito.`when`(currencyLayerRepository.getSupportedCurrencyList())
            .thenReturn(Single.just(currencyResponse))
        Mockito.`when`(currencyLayerRepository.getLiveExchangeRate())
            .thenReturn(Single.just(currencyRateResponse))
        getCurrencyRates.execute()
        val response = getCurrencyRates.exchangeRates("USDUSD", 1.0)

        Assert.assertEquals(expected.rates.size, response.rates.size)
    }
}
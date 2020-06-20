package com.ssh.currencyconvert

import com.ssh.currencyconvert.repositories.CurrencyLayerApi
import com.ssh.currencyconvert.repositories.CurrencyLayerRepository
import com.ssh.currencyconvert.repositories.CurrencyLayerRepositoryImpl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import utils.MockResponseFileReader
import java.util.concurrent.TimeUnit

class GetCurrenciesTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    lateinit var currencyLayerRepository: CurrencyLayerRepository
    lateinit var currencyLayerApi: CurrencyLayerApi

    lateinit var server: MockWebServer

    @Before
    fun initTest() {
        server = MockWebServer()
        server.start(8000)
        currencyLayerApi = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CurrencyLayerApi::class.java)

        currencyLayerRepository = CurrencyLayerRepositoryImpl(currencyLayerApi)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    /**
     * Response onSuccess test
     *
     * test criteria:
     * provided listof items.
     * assertion equality check from response with provided values.
     */
    @Test
    fun executeGetCurrencies() {

        server.apply {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(MockResponseFileReader("test_currencies.json").content)
            )
        }

        currencyLayerRepository.getSupportedCurrencyList()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it.currencies["AED"] == "United Arab Emirates Dirham" }
            .assertNoErrors()
    }
}
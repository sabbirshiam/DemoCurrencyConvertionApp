package com.ssh.currencyconvert.models

import com.squareup.moshi.Json

data class CurrencyRateQuotes(
    @field:Json(name = "privacy")
    var privacy: String,
    @field:Json(name = "quotes")
    var quotes: Map<String, Double>,
    @field:Json(name = "source")
    var source: String,
    @field:Json(name = "success")
    var success: Boolean,
    @field:Json(name = "terms")
    var terms: String,
    @field:Json(name = "timestamp")
    var timestamp: Int
)
package com.ssh.currencyconvert.models

import com.squareup.moshi.Json

data class Currencies(
    @field:Json(name = "currencies")
    var currencies: Map<String, String>,
    @field:Json(name = "privacy")
    var privacy: String,
    @field:Json(name = "success")
    var success: Boolean,
    @field:Json(name = "terms")
    var terms: String
)
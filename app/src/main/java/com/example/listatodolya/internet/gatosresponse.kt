package com.example.listatodolya.internet

import com.google.gson.annotations.SerializedName

data class gatosresponse (
    @SerializedName("fact") var frase: String,
    @SerializedName("data") var datos: List<String>
        )

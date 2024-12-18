package com.app.countrycodeselect.utils

import com.google.gson.annotations.SerializedName

class CountryListModel : ArrayList<CountryListModel.CountryListModelItem>() {
    data class CountryListModelItem(
        @SerializedName("code")
        val code: String,
        @SerializedName("dial_code")
        val dialCode: String,
        @SerializedName("name")
        val name: String
    )
}
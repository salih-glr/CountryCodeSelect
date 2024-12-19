package com.app.countrycode.utils

import android.content.Context
import android.widget.ImageView
import com.app.countrycode.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.io.BufferedReader
import java.util.Locale

object CountryListUtils {

    fun getCurrentCountry(context: Context): CountryListModel.CountryListModelItem {
        val list = getCountryList(context)
        val country = Locale.getDefault().country
        list.forEach {
            if (it.code.contentEquals(country, true)) return it
        }
        return CountryListModel.CountryListModelItem(
            "US",
            "+1",
            "United States"
        )
    }



    fun getCountryList(context: Context): CountryListModel {
        val inputStream = context.resources.openRawResource(R.raw.country_list)
        val bufferedReader = BufferedReader(inputStream.reader())
        val sb = StringBuilder()
        bufferedReader.use { reader ->
            var line = reader.readLine()
            while (line != null) {
                sb.append(line + "\n")
                line = reader.readLine()
            }
        }
        return Gson().fromJson(sb.toString(), CountryListModel::class.java)
    }

    fun findCountryData(
        context: Context,
        code: String
    ): CountryListModel.CountryListModelItem? {
        return getCountryList(context).find { it.dialCode == code }
    }


    val String.localizedCountryName: String
        get() = Locale("", this).getDisplayCountry(Locale.ROOT)


    fun loadCountryImage(countryCode: String, imageView: ImageView) {
        Glide.with(imageView)
            .load("https://flagcdn.com/w160/${countryCode.lowercase()}.png")
            .fitCenter()
            .centerCrop()
            .into(imageView)
    }


}

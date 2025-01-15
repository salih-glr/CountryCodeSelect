package com.app.countrycodeselect


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.countrycode.CountryCodeActivity
import com.app.countrycode.utils.CountryListModel
import com.app.countrycode.utils.CountryListUtils
import com.app.countrycodeselect.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var currentCountry: CountryListModel.CountryListModelItem


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        currentCountry = CountryListUtils.getCurrentCountry(this)

        setOnClickListener()
        initData()
        setContentView(binding.root)

    }

    private fun initData() {
        CountryListUtils.loadCountryImage(currentCountry.code, findViewById(R.id.iFlag))
        findViewById<TextView>(R.id.tPhoneCountryCode).text = currentCountry.dialCode
    }

    private fun setOnClickListener() {
        binding.bSelectedCountry.setOnClickListener {
            selectedCountry()
        }


    }

    private fun selectedCountry() {
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val selectedCountry = Gson().fromJson(
                    result.data!!.getStringExtra("data"),
                    CountryListModel.CountryListModelItem::class.java
                )
                // Seçilen ülkeyi işleyin
            }
        }

        launcher.launch(Intent(this, CountryCodeActivity::class.java))
    }


    companion object {
        private val TAG = "MainActivity"
    }
}
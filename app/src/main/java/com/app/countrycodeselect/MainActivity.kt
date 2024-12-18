package com.app.countrycodeselect

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.countrycodeselect.databinding.ActivityMainBinding
import com.app.countrycodeselect.utils.CountryListModel
import com.app.countrycodeselect.utils.CountryListUtils
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var currentCountry: CountryListModel.CountryListModelItem
    private lateinit var textFormatter: PhoneNumberFormattingTextWatcher
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        this::onCountryPicked
    )

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        currentCountry = CountryListUtils.getCurrentCountry(this)
        updatePhonePicker()
        setOnClickListener()


        setContentView(binding.root)

    }

    private fun setOnClickListener() {
        binding.bSelectedCountry.setOnClickListener {
            launcher.launch(Intent(this, CountryCodeActivity::class.java))
        }
    }

    private fun onCountryPicked(result: ActivityResult) {
        if (result.resultCode != 0 || result.data?.getStringExtra("data") == null) return
        currentCountry = Gson().fromJson(
            result.data!!.getStringExtra("data"),
            CountryListModel.CountryListModelItem::class.java
        )
        binding.tPhoneCountryCode.text = currentCountry.dialCode
        CountryListUtils.loadCountryImage(currentCountry.code, binding.iFlag)

    }

    private fun updatePhonePicker() {
        // Telefon numarası her değiştikçe formatlamaya yarar.
        if (this::textFormatter.isInitialized) binding.etPhoneNumber.removeTextChangedListener(
            textFormatter
        )
        textFormatter = PhoneNumberFormattingTextWatcher(currentCountry.code)
        Log.d(TAG, "updatePhonePicker() called currentCountry.code:${currentCountry.code}")
        binding.etPhoneNumber.addTextChangedListener(textFormatter)
        textFormatter.afterTextChanged(binding.etPhoneNumber.text)
        CountryListUtils.loadCountryImage(currentCountry.code, binding.iFlag)
        binding.tPhoneCountryCode.text = currentCountry.dialCode
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
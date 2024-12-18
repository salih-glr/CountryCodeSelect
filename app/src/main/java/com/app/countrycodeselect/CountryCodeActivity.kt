package com.app.countrycodeselect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.countrycodeselect.adapter.CountryPickerAdapter
import com.app.countrycodeselect.databinding.ActivityCountryCodeBinding
import com.app.countrycodeselect.utils.CountryListUtils
import com.google.gson.Gson
import androidx.core.widget.addTextChangedListener

class CountryCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryCodeBinding
    private lateinit var adapter: CountryPickerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(
            savedInstanceState)
        binding = ActivityCountryCodeBinding.inflate(layoutInflater)
        initUI()
        setOnClickListener()

        setContentView(binding.root)
    }
    private fun setOnClickListener(){
        binding.ivCLose.setOnClickListener {
            finish()
        }
    }


    private fun initUI() {
        adapter = CountryPickerAdapter(CountryListUtils.getCountryList(this)) {
            setResult(0, Intent().apply {
                putExtra("data", Gson().toJson(it))
            })
            finish()
        }
        binding.recyclerView.adapter = adapter
        binding.etSearch.addTextChangedListener {
            adapter.searchText(it.toString())
        }
    }
}
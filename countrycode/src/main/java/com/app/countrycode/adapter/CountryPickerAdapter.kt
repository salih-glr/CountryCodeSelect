package com.app.countrycode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class CountryPickerAdapter(
    private val list: CountryListModel,
    private val onCountryPicked: (CountryListModel.CountryListModelItem) -> Unit
) : RecyclerView.Adapter<CountryPickerAdapter.VH>() {

    private var filteredList = ArrayList(list)

    init { // This beautiful line makes RecyclerView more smooth & animated.
        setHasStableIds(true)
    }


    inner class VH(
        private val binding: ListItemChooseCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: CountryListModel.CountryListModelItem) {
            val locale = Locale("", data.code) // ISO ülke kodu
            binding.countryName.text = locale.displayCountry // Ülkenin localized ismini al

            binding.root.setOnClickListener { onCountryPicked(data) }
            CountryListUtils.loadCountryImage(data.code, binding.ivCountyFlag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val lai = LayoutInflater.from(parent.context)
        return VH(ListItemChooseCountryBinding.inflate(lai, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setData(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun searchText(searchStr: String) {
        val newList = list.filter {
            val countryName = it.code.localizedCountryName.lowercase()
            countryName.contains(searchStr.lowercase())
        }
        // if the lists are exact same, there's no need to update. Otherwise stuttering would be seen at animations.
        if (newList.sameContentWith(filteredList) == true) return
        filteredList.clear()
        filteredList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return filteredList[position].dialCode.substring(1).trim().toLong()
    }

    companion object {
        private const val TAG = "CountryPickerAdapter"
    }

    /**
     * Checks the list with all of its contents. If they have same size & order; this will return true.
     */
    private infix fun <T> Collection<T>.sameContentWith(collection: Collection<T>?) =
        collection?.let { this.size == it.size && this.containsAll(it) }
}
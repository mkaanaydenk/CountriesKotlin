package com.mehmetkaanaydenk.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.kotlincountries.R
import com.mehmetkaanaydenk.kotlincountries.databinding.ItemCountryBinding
import com.mehmetkaanaydenk.kotlincountries.model.Country
import com.mehmetkaanaydenk.kotlincountries.view.FeedFragmentDirections


class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

    class CountryHolder(val countryBinding: ItemCountryBinding) :
        RecyclerView.ViewHolder(countryBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.countryBinding.countryName.text = countryList[position].countryName
        holder.countryBinding.region.text = countryList[position].countryRegion
        holder.itemView.setOnClickListener {

            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)

        }
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()


    }

}
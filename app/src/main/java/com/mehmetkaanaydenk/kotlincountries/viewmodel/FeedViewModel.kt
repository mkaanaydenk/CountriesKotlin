package com.mehmetkaanaydenk.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.kotlincountries.model.Country

class FeedViewModel : ViewModel() {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData() {

        val country = Country("Turkey", "Asia", "Ankara", "TRY", "Turkish", "www.ss.com")
        val country2 = Country("France", "Europe", "Paris", "EUR", "French", "www.ss.com")
        val country3 = Country("USA", "America", "Washington", "USD", "English", "www.ss.com")

        val countryList = arrayListOf<Country>(country, country2, country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false


    }

}
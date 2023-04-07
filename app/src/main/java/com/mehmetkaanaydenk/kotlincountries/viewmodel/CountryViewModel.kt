package com.mehmetkaanaydenk.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.kotlincountries.model.Country

class CountryViewModel : ViewModel() {

    val country = MutableLiveData<Country>()

    fun refreshData(){

        val country1 = Country("Turkey","Asia","Ankara","TRY","Turkish","www.ss.com")

        country.value = country1


    }

}
package com.mehmetkaanaydenk.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.kotlincountries.model.Country
import com.mehmetkaanaydenk.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun refreshData(uuid: Int) {

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountryById(uuid)
            countryLiveData.value = country

        }


    }

}
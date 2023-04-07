package com.mehmetkaanaydenk.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetkaanaydenk.kotlincountries.model.Country
import com.mehmetkaanaydenk.kotlincountries.service.CountryAPIService
import com.mehmetkaanaydenk.kotlincountries.service.CountryDatabase
import com.mehmetkaanaydenk.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val api = CountryAPIService()
    private val disposable = CompositeDisposable()

    private var customPreferences = CustomSharedPreferences(getApplication())

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    fun refreshData() {
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromApi()
        }

    }

    fun refreshFromApi() {

        getDataFromApi()

    }

    fun getDataFromSQLite() {
        countryLoading.value = true
        launch {

            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_LONG).show()

        }

    }

    fun getDataFromApi() {

        countryLoading.value = true

        disposable.add(

            api.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeData(t)
                        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })


        )

    }

    private fun showCountries(countryList: List<Country>) {

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }

    private fun storeData(list: List<Country>) {

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())

            var i = 0

            while (i < list.size) {

                list[i].uuid = listLong[i].toInt()
                i += 1

            }
            showCountries(list)

        }
        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
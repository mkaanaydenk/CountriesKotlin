package com.mehmetkaanaydenk.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.mehmetkaanaydenk.kotlincountries.R
import com.mehmetkaanaydenk.kotlincountries.databinding.FragmentCountryBinding
import com.mehmetkaanaydenk.kotlincountries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryViewModel
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.refreshData()
        arguments?.let {

            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid

        }
        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.country.observe(viewLifecycleOwner, Observer {

            it?.let {

                binding.name.text = it.countryName
                binding.capital.text = it.countryCapital
                binding.region.text = it.countryRegion
                binding.currency.text = it.countryCurrency
                binding.language.text = it.countryLanguage

            }

        })

    }

}
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
import com.mehmetkaanaydenk.kotlincountries.util.downloadFromUrl
import com.mehmetkaanaydenk.kotlincountries.util.placeholderProgressBar
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
        arguments?.let {

            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid

        }
        viewModel.refreshData(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->

            country?.let {

                binding.name.text = country.countryName
                binding.capital.text = country.countryCapital
                binding.region.text = country.countryRegion
                binding.currency.text = country.countryCurrency
                binding.language.text = country.countryLanguage
                context?.let {
                    binding.countryImage.downloadFromUrl(
                        country.imageUrl,
                        placeholderProgressBar(it)
                    )
                }


            }

        })

    }

}
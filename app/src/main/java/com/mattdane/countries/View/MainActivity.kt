package com.mattdane.countries.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mattdane.countries.Adapter.CountryListAdapter

import com.mattdane.countries.ViewModel.ListViewModel
import com.mattdane.countries.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // view binding for the activity
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ListViewModel
    private val countryAdapter =  CountryListAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()


        binding.countriesRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryAdapter
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let { countryAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(this , Observer { isError ->
            isError?.let { binding.errorTXT.visibility = if(it) View.VISIBLE else View.GONE}
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let { binding.loadingPB.visibility = if(it) View.VISIBLE else
            View.GONE
             if(it){
                 binding.errorTXT.visibility = View.GONE
                 binding.loadingPB.visibility = View.GONE
             }
            }
        })
    }



}
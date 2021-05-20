package com.mattdane.countries.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mattdane.countries.Model.Country
import com.mattdane.countries.R
import com.mattdane.countries.databinding.ItemCountryBinding


class CountryListAdapter(var countries: ArrayList<Country>, val context: Context):RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>(){

    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        with(holder){
            with(countries[position]){
                binding.nameTXT.text = this.countryName
                binding.capitalTXT.text = this.capital
               Glide.with(context).load(this.flag).error(R.drawable.ic_launcher_background).into(binding.flagIMG)
            }
        }
    }

    inner class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

}
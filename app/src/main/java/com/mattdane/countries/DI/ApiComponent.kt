package com.mattdane.countries.DI

import com.mattdane.countries.Model.CountryService
import com.mattdane.countries.ViewModel.ListViewModel
import dagger.Component
import javax.inject.Inject

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountryService)
    fun inject(viewModel: ListViewModel)
}
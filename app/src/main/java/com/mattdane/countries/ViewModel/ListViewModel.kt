package com.mattdane.countries.ViewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mattdane.countries.DI.DaggerApiComponent


import com.mattdane.countries.Model.Country
import com.mattdane.countries.Model.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel: ViewModel() {


    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    @Inject
    lateinit var countriesService : CountryService
      init{
         DaggerApiComponent.create().inject(this)
      }


    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries() {
       loading.value = true
       disposable.add(
               countriesService.getCountries()
                       .subscribeOn(Schedulers.newThread())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                           override fun onSuccess(value: List<Country>?) {
                               countries.value = value
                               countryLoadError.value = false
                               loading.value = false
                           }

                           override fun onError(e: Throwable?) {
                               countryLoadError.value = true
                               loading.value = false
                           }

                       })
       )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
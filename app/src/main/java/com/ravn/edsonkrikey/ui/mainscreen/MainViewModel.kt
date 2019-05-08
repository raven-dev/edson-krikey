package com.ravn.edsonkrikey.ui.mainscreen

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.core.BaseViewModel
import com.ravn.edsonkrikey.repository.ItunesRepository
import com.ravn.edsonkrikey.repository.remote.ItunesItems
import com.ravn.edsonkrikey.repository.remote.Label
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class MainViewModel(val app: App): BaseViewModel(app) {
    @Inject lateinit var itunesRepository: ItunesRepository
    val viewModelState = MutableLiveData<MainState>()
    var listOfItems = listOf<ItunesItems>()
    val loading = ObservableBoolean(false)
    val noResults = ObservableBoolean(false)

    override fun onStart() {
        super.onStart()
        app.component.inject(this)
    }

    fun search(searchKey: String) {
        loading.set(true)
        noResults.set(false)
        itunesRepository.searchItunes(searchKey)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                listOfItems = response.results
                loading.set(false)
                viewModelState.value = MainState.SUCCESS
            },{
                loading.set(false)
                viewModelState.value = MainState.ERROR
            }).addTo(subscriptions)

    }

    fun getCategories(): Map<String, List<ItunesItems>> {
        return listOfItems.groupBy {
            it.kind
        }
    }

    fun completeListOfItems(): List<Any> {
        val list = mutableListOf<Any>()
        getCategories().forEach {
            list.add(Label(it.key+"s"))
            list.addAll(it.value)
        }
        return list
    }

    companion object {
        enum class MainState {
            SUCCESS, ERROR
        }
    }
}
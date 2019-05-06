package com.ravn.edsonkrikey.repository

import com.ravn.edsonkrikey.repository.remote.Data
import io.reactivex.Observable
import retrofit2.Retrofit

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class ItunesRepository(val api: Retrofit) {
    fun searchItunes(findValue: String): Observable<Data> {
        return api.create(RequestInterface::class.java).searchItunes(findValue)
    }
}
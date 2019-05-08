package com.ravn.edsonkrikey.repository

import com.ravn.edsonkrikey.di.modules.NetworkModule
import com.ravn.edsonkrikey.repository.remote.Data
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

interface RequestInterface {
    @GET(NetworkModule.SEARCH)
    fun searchItunes(
        @Query(NetworkModule.FIND_VALUE) value: String): Observable<Data>
}
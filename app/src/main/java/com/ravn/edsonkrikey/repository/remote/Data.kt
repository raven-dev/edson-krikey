package com.ravn.edsonkrikey.repository.remote

import java.util.*

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

data class Data (
    val resultCount: Int,
    val results: List<ItunesItems>
)

data class ItunesItems(
    val wrapperType : String,
    val kind : String,
    val artistId : String,
    val collectionId : String,
    val trackId : String,
    val artistName : String,
    val collectionName : String,
    val trackName : String,
    val artistViewUrl : String,
    val artworkUrl100 : String,
    val collectionPrice : Float,
    val trackPrice : Float,
    val releaseDate : Date,
    val primaryGenreName : String
)
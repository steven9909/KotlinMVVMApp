package com.example.championlike.Data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ChampionEndpoint {


    @GET("cdn/{version}/{locale}/champion.json")
    fun getChampionNames(@Path("version") version:String, @Path("locale") locale:String): Single<ArrayList<String>>

}
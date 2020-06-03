package com.example.championlike.Data.Endpoint

import android.graphics.Bitmap
import com.example.championlike.Data.Model.Champion
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ChampionEndpoint {

    @GET("api/versions.json")
    fun getLatestVersion():Single<ArrayList<String>>

    @GET("cdn/{version}/data/{locale}/champion.json")
    fun getChampionNames(@Path("version") version:String, @Path("locale") locale:String): Single<List<Champion>>

    @GET("cdn/{version}/data/{locale}/champion/{championName}.json")
    fun getChampionDetail(@Path("version") version:String ,@Path("locale") locale:String ,@Path("championName") name:String):Single<Champion>
}
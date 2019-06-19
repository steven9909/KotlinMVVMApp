package com.example.championlike.Data

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class ChampionAPI @Inject constructor(@Named("locale") val locale:String, @Named("version") val version:String):ChampionEndpoint{

    @Inject
    lateinit var championEndpoint:ChampionEndpoint

    override fun getChampionNames(): Single<ArrayList<String>> {
        return championEndpoint.getChampionNames(version,locale)
    }



}
package com.example.championlike.Data

import android.content.SharedPreferences
import com.example.championlike.Data.Endpoint.ChampionEndpoint
import com.example.championlike.Data.Model.Champion
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.*

class ChampionAPI constructor(val championEndpoint: ChampionEndpoint, val locale:String, pref: SharedPreferences){

    private var version:String? = null

    init{

        fallbackVersion = pref.getString(VERSION_TAG,"9.13.1")

        championEndpoint.getLatestVersion()
            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                version = t1[0]
                pref.edit().putString(VERSION_TAG,t1[0])
        }
    }

    companion object{
        private lateinit var fallbackVersion:String
        private val VERSION_TAG:String = "version_tag"
    }

    fun getChampionNames(): Single<List<Champion>> {
        return championEndpoint.getChampionNames(version ?: fallbackVersion,locale)
    }


    fun getChampionDetail(name:String): Single<Champion>{
        return championEndpoint.getChampionDetail(version ?: fallbackVersion,locale,name)
    }




}
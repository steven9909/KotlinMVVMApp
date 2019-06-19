package com.example.championlike.Domain

import io.reactivex.Observable

interface ChampionRepository {

    fun getChampionNames(): Observable<ArrayList<String>>

    fun getChampions()

}
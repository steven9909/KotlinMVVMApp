package com.example.championlike.Data

import com.example.championlike.Domain.ChampionRepository
import io.reactivex.Observable
import javax.inject.Inject

class ChampionRepositoryImpl @Inject constructor() : ChampionRepository {

    @Inject
    lateinit var champAPI:ChampionAPI

    override fun getChampionNames(): Observable<ArrayList<String>> {
        return champAPI.getChampionNames().toObservable()
    }

    override fun getChampions() {

    }
}
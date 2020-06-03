package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Repository.ChampionRepositoryEndpoint
import io.reactivex.Observable

class LoadChampionNamesUseCase constructor(val localRepository: ChampionRepositoryEndpoint){

    fun execute(): Observable<List<Champion>> {
        return localRepository.getChampionNames()
    }
}
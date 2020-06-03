package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Repository.ChampionRepositoryEndpoint
import com.example.championlike.Domain.Repository.ChampionRepository
import io.reactivex.Observable

class LoadChampionUseCase(val repository: ChampionRepositoryEndpoint){

    fun execute(name:String):Observable<Champion>{
        return repository.getChampionDetail(name)
    }

}
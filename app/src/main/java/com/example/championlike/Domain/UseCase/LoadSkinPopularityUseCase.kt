package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Data.Repository.ChampionRepositoryFirebase
import io.reactivex.Observable

class LoadSkinPopularityUseCase (val remoteRepository:ChampionRepositoryFirebase){

    fun execute(list:List<SkinEntity>): Observable<Boolean> {
        return remoteRepository.getChampionPopularity(list)
    }

}
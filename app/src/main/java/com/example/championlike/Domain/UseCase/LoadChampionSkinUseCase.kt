package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Data.Repository.ChampionRepositoryDao
import com.example.championlike.Data.Repository.ChampionRepositoryEndpoint
import com.example.championlike.Domain.Repository.ChampionRepository
import io.reactivex.Observable

class LoadChampionSkinUseCase(val localRepository: ChampionRepositoryDao){

    fun execute(name:String): Observable<List<SkinEntity>> {
        return localRepository.getChampionSkin(name)
    }

}
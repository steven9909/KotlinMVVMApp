package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Data.Repository.ChampionRepositoryDao
import com.example.championlike.Data.Repository.ChampionRepositoryFirebase

class SaveChampionSkinUseCase constructor(val localRepository:ChampionRepositoryDao, val remoteRepository:ChampionRepositoryFirebase){

    fun execute(entity:SkinEntity){
        localRepository.saveChampionSkin(entity)
        //remoteRepository.saveChampionSkin(entity)
    }

}
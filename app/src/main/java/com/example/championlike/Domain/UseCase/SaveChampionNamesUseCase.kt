package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Repository.ChampionRepositoryDao
import com.example.championlike.Data.Repository.ChampionRepositoryFirebase

class SaveChampionNamesUseCase constructor(val localRepository:ChampionRepositoryDao, val remoteRepository:ChampionRepositoryFirebase){

    fun execute(list:List<Champion>){
        localRepository.saveChampionNames(list)
        remoteRepository.saveChampionNames(list)
        //localRepository.clearRepository()
    }

}
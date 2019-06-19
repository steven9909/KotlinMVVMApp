package com.example.championlike.Domain.UseCase

import com.example.championlike.Component.DaggerUseCaseComponent
import com.example.championlike.Component.UseCaseComponent
import com.example.championlike.Domain.ChampionRepository
import com.example.championlike.Module.ChampionRepositoryModule
import javax.inject.Inject

class LoadChampionNamesUseCase constructor(){

    @Inject
    lateinit var repository:ChampionRepository

    private lateinit var useCaseComponent: UseCaseComponent

    init{
        useCaseComponent = DaggerUseCaseComponent.builder().championRepositoryModule(ChampionRepositoryModule()).build()
    }

    fun execute(){

    }
}
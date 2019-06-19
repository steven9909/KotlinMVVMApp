package com.example.championlike.Component

import com.example.championlike.Domain.ChampionRepository
import com.example.championlike.Domain.ChampionViewModel
import com.example.championlike.MainActivity
import com.example.championlike.Module.ChampionAPIModule
import com.example.championlike.Module.ChampionEndpointModule
import com.example.championlike.Module.ChampionRepositoryModule
import dagger.Component

@Component(modules=arrayOf(ChampionRepositoryModule::class, ChampionEndpointModule::class, ChampionAPIModule::class))
interface UseCaseComponent {

    fun provideChampionRepository():ChampionRepository

    fun inject(main: ChampionViewModel)
}
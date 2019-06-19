package com.example.championlike.Module

import com.example.championlike.Data.ChampionRepositoryImpl
import com.example.championlike.Domain.ChampionRepository
import dagger.Module
import dagger.Provides

@Module
class ChampionRepositoryModule{

    @Provides
    fun provideChampionRepository(): ChampionRepository {
        return ChampionRepositoryImpl()
    }

}
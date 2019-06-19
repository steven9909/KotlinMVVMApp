package com.example.championlike.Module

import com.example.championlike.Data.ChampionEndpoint
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
public class ChampionEndpointModule{

    @Provides
    fun providesChampionEndpoint():ChampionEndpoint{
        return Retrofit.Builder().baseUrl("http://ddragon.leagueoflegends.com/cdn").build().create(ChampionEndpoint::class.java)
    }

}
package com.example.championlike.Module

import com.example.championlike.Data.ChampionAPI
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ChampionAPIModule{

    @Provides
    fun provideChampionAPI(@Named("locale") locale:String, @Named("version") version:String):ChampionAPI{
        return ChampionAPI(locale,version)
    }

    @Provides
    @Named("locale")
    fun provideLocale():String{
        return "en_US"
    }

    @Provides
    @Named("version")
    fun provideVersion():String{
        return "9.3.1"
    }

}
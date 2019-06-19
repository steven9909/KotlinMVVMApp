package com.example.championlike.Domain

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Domain.UseCase.LoadChampionNamesUseCase
import javax.inject.Inject

class ChampionViewModel(): ViewModel(){

    private val champions: MutableLiveData<List<Champion>> = MutableLiveData()

    private lateinit var nameUseCase:LoadChampionNamesUseCase

    companion object{
        private val championNames:List<String> = ArrayList<String>()
    }

    fun getChampions():MutableLiveData<List<Champion>>{
        return champions
    }

    /**
     * Call this method first to initialize champion names
     */
    fun bind(){
        nameUseCase = LoadChampionNamesUseCase()
    }

}
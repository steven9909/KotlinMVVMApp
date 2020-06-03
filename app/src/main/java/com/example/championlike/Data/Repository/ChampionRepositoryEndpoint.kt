package com.example.championlike.Data.Repository

import com.example.championlike.Data.ChampionAPI
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Data.Model.TaskResult
import com.example.championlike.Domain.Repository.ChampionRepository
import io.reactivex.Observable

class ChampionRepositoryEndpoint constructor(val champAPI: ChampionAPI) :
    ChampionRepository {

    override fun getChampionNames(): Observable<List<Champion>> {
        return champAPI.getChampionNames().toObservable()
    }

    override fun getChampionDetail(name:String): Observable<Champion> {
        return champAPI.getChampionDetail(name).toObservable()
    }

    override fun saveChampionNames(list:List<Champion>){
        throw NotImplementedError("This method is not supported for remote endpoints")
    }

    override fun getChampionSkin(name:String): Observable<List<SkinEntity>>{
        throw NotImplementedError("This method is not supported for remote endpoints")
    }

    override fun updateSkinEntity(name:String, id:Int, liked:Boolean, disliked:Boolean, skinEntity: SkinEntity):Observable<TaskResult<SkinEntity>>?{
        throw NotImplementedError("This method is not supported for remote endpoints")
    }

    override fun saveChampionSkin(entity:SkinEntity) {
        throw NotImplementedError("This method is not supported for remote endpoints")
    }

    override fun clearRepository() {
        throw NotImplementedError("This method is not supported for remote endpoints")
    }

    override fun getChampionPopularity(skins:List<SkinEntity>): Observable<Boolean>{
        throw NotImplementedError("This method is not supported for remote endpoints")
    }
}
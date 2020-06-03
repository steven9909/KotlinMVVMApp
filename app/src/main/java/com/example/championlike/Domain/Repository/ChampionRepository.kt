package com.example.championlike.Domain.Repository

import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.TaskResult
import com.example.championlike.Data.Model.SkinEntity
import io.reactivex.Observable

interface ChampionRepository {

    fun getChampionNames(): Observable<List<Champion>>

    fun getChampionDetail(name:String): Observable<Champion>

    fun getChampionSkin(name:String): Observable<List<SkinEntity>>

    fun saveChampionNames(list:List<Champion>)

    fun saveChampionSkin(entity:SkinEntity)

    fun updateSkinEntity(name:String, id:Int, liked:Boolean, disliked:Boolean, skinEntity: SkinEntity):Observable<TaskResult<SkinEntity>>?

    fun clearRepository()

    fun getChampionPopularity(skins:List<SkinEntity>): Observable<Boolean>
}
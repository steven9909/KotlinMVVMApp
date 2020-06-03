package com.example.championlike.Data.Repository

import com.example.championlike.Data.Database.Remote.ChampionFirebase
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.TaskResult
import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Domain.Repository.ChampionRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ChampionRepositoryFirebase(val firebase: ChampionFirebase) : ChampionRepository{

    override fun getChampionNames(): Observable<List<Champion>> {
        return firebase.getChampionNames()
    }

    override fun getChampionDetail(name: String): Observable<Champion> {
        throw NotImplementedError("Not supported for remote database")
    }

    override fun getChampionSkin(name: String): Observable<List<SkinEntity>> {
        throw NotImplementedError("Not supported for remote database")
    }

    override fun saveChampionNames(list: List<Champion>) {
        firebase.saveChampionNames(list)
    }

    override fun saveChampionSkin(entity: SkinEntity) {
        firebase.saveChampionSkin(entity)
    }

    override fun updateSkinEntity(name: String, id: Int, liked: Boolean, disliked: Boolean, skinEntity: SkinEntity):Observable<TaskResult<SkinEntity>>?{

        if(skinEntity.liked){

            if(disliked){
                return firebase.updateSkinPopularity(skinEntity,false,true,true,false)
            }

        }
        else if(skinEntity.disliked){

            if(liked){
                return firebase.updateSkinPopularity(skinEntity,true,false,false,true)
            }

        }
        else{

            if(liked){
                return firebase.updateSkinPopularity(skinEntity,true,false,false,false)
            }
            else{
                return firebase.updateSkinPopularity(skinEntity,false,true,false,false)
            }

        }

        return null

    }

    override fun getChampionPopularity(skins:List<SkinEntity>): Observable<Boolean>{
        return firebase.getAllSkinsPopularityNumber(skins)
    }

    override fun clearRepository() {
        throw NotImplementedError("Not supported for remote database")
    }

}
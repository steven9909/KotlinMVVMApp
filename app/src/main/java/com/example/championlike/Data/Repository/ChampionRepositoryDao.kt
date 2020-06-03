package com.example.championlike.Data.Repository

import android.util.Log
import com.example.championlike.Data.Database.Local.ChampionDao
import com.example.championlike.Data.Database.Local.ChampionDatabase
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Data.Model.TaskResult
import com.example.championlike.Domain.Repository.ChampionRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ChampionRepositoryDao constructor(database: ChampionDatabase):
    ChampionRepository {

    private lateinit var dao: ChampionDao

    init{
        dao = database.championDaoAccess()
    }

    override fun getChampionDetail(name: String): Observable<Champion> {
        throw NotImplementedError("Not implemented for local repository")
    }

    override fun getChampionNames(): Observable<List<Champion>> {
        return dao.loadAllChampions().toObservable()
    }

    override fun saveChampionNames(list:List<Champion>) {
        dao.insertChampions(list)
    }

    override fun getChampionSkin(name:String): Observable<List<SkinEntity>> {
        return dao.getSkinsOfChampion(name).toObservable()
    }

    override fun saveChampionSkin(entity:SkinEntity) {
        dao.insertSkin(entity)
    }

    override fun updateSkinEntity(name:String, id:Int, liked:Boolean, disliked:Boolean, skinEntity: SkinEntity):Observable<TaskResult<SkinEntity>>?{
        return dao.updateSkin(name,id,liked,disliked).toObservable().map{
            TaskResult(it>0,skinEntity)
        }
    }

    override fun clearRepository() {
        dao.deleteSkinTable()
    }

    override fun getChampionPopularity(skins:List<SkinEntity>): Observable<Boolean>{
        throw NotImplementedError("Not supported for local repository")
    }

}
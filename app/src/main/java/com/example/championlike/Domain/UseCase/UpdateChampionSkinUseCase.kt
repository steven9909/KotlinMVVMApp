package com.example.championlike.Domain.UseCase

import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Data.Model.TaskResult
import com.example.championlike.Data.Repository.ChampionRepositoryDao
import com.example.championlike.Data.Repository.ChampionRepositoryFirebase
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UpdateChampionSkinUseCase (val localRepository:ChampionRepositoryDao, val remoteRepository:ChampionRepositoryFirebase){

    fun execute(name:String, id:Int, liked:Boolean, disliked:Boolean, skinEntity: SkinEntity, localRepositoryOnly: Boolean): Observable<TaskResult<SkinEntity>>?{
        localRepository.updateSkinEntity(name, id, liked, disliked, skinEntity)?.let{
            it.subscribeOn(Schedulers.io())
                .subscribe(object: Observer<TaskResult<SkinEntity>>{

                    var dispose:Disposable? = null

                    override fun onComplete() {
                        dispose?.dispose()
                    }

                    override fun onError(e: Throwable) {
                        dispose?.dispose()
                    }

                    override fun onNext(t: TaskResult<SkinEntity>) {

                    }

                    override fun onSubscribe(d: Disposable) {
                        dispose = d
                    }
                })

        }

        if(localRepositoryOnly){
            return null
        }

        return remoteRepository.updateSkinEntity(name,id,liked,disliked,skinEntity)
    }

}
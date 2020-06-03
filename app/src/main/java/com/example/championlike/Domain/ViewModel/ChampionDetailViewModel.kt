package com.example.championlike.Domain.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.SkinEntity
import com.example.championlike.Domain.UseCase.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ChampionDetailViewModel (val loadChampion: LoadChampionUseCase, val saveSkin:SaveChampionSkinUseCase, val loadSkin:LoadChampionSkinUseCase, val updateSkin:UpdateChampionSkinUseCase, val getSkinPopularity:LoadSkinPopularityUseCase) : ViewModel() {

    private lateinit var name:String

    val championDetail: MutableLiveData<Champion> = MutableLiveData()

    val championSkin: MutableLiveData<List<SkinEntity>> = MutableLiveData()

    private val compositeDisposable:CompositeDisposable = CompositeDisposable()

    private lateinit var skinList:MutableList<SkinEntity>


    fun bind(name:String){

        this.name = name

        loadChampion.execute(name)
            .subscribeOn(Schedulers.io())
            .subscribe(object: Observer<Champion> {
                override fun onComplete() {

                }
                override fun onError(e: Throwable) {

                }
                override fun onNext(t: Champion) {

                    val list:List<SkinEntity> = loadSkin.execute(name).blockingSingle()

                    skinList = list.toMutableList()

                    t.skins?.let{
                        for(key in it.keys){
                            var found = false
                            for(skin in skinList){
                                if(skin.skinId == key){
                                    if(skin.liked){
                                        t.liked.put(key,true)
                                    }
                                    else if(skin.disliked){
                                        t.disliked.put(key,true)
                                    }
                                    found = true
                                    it.get(key)?.let{skinName->
                                        skin.skinName = skinName
                                    }

                                    break
                                }
                            }

                            if(!found){
                                val skin = SkinEntity(name,key,false,false,it.get(key)!!.replace("/",""))
                                saveSkin.execute(skin)
                                skinList.add(skin)
                            }

                        }
                    }

                    getSkinPopularity.execute(skinList).blockingLast()

                    championSkin.postValue(skinList)
                    championDetail.postValue(t)
                }
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
            })


    }

    fun likeOrDislikeButtonPressed(id:Int, likeButtonPressed:Boolean){
        if(this::skinList.isInitialized) {
            val currentSkin:SkinEntity? = skinList.find{
                it.skinId == id
            }

            currentSkin?.let {

                updateSkin.execute(name, id, likeButtonPressed, !likeButtonPressed, currentSkin, false)?.let {
                    val dispose: Disposable = it.subscribeOn(Schedulers.io())
                        .subscribeBy(
                            onNext = { task ->
                                if (task.isSuccessful) {
                                    updateSkin.execute(name, id, likeButtonPressed, !likeButtonPressed, currentSkin, true)
                                    championSkin.postValue(skinList)
                                }
                            },
                            onError = {

                            },
                            onComplete = {

                            }
                        )

                    compositeDisposable.add(dispose)
                }

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }


}
package com.example.championlike.Domain.ViewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Domain.UseCase.LoadChampionNamesUseCase
import com.example.championlike.Domain.UseCase.SaveChampionNamesUseCase
import com.example.championlike.Domain.UseCase.SaveChampionSplashUseCase
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class ChampionViewModel(val nameUseCase:LoadChampionNamesUseCase, val saveUseCase:SaveChampionNamesUseCase): ViewModel(){

    val champions: MutableLiveData<List<Champion>> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    companion object {

        private val REL_CHAMPION_DIRECTORY = "champion/splash"

        private lateinit var baseImagePath:File
    }

    /**
     * Call this method first to initialize champion names
     */
    fun bind(basePath:File?){
        val championNames: Observable<List<Champion>> = nameUseCase.execute()

        val path:File = File(basePath,
            REL_CHAMPION_DIRECTORY
        )

        path.mkdir()

        baseImagePath = path

        championNames
            .subscribeOn(Schedulers.io())
            .subscribe(object: Observer<List<Champion>> {
                override fun onComplete() {
                }

                override fun onError(e: Throwable) {

                }

                override fun onNext(t: List<Champion>) {
                    champions.postValue(t)
                    saveUseCase.execute(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
            })

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}
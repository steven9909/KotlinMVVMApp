package com.example.championlike.module

import androidx.room.Room
import com.example.championlike.Data.ChampionAPI
import com.example.championlike.Data.Endpoint.ChampionEndpoint
import com.example.championlike.Data.Repository.ChampionRepositoryDao
import com.example.championlike.Data.Repository.ChampionRepositoryEndpoint
import com.example.championlike.Data.Database.Local.ChampionDatabase
import com.example.championlike.Data.Database.Remote.ChampionFirebase
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Repository.ChampionRepositoryFirebase
import com.example.championlike.Domain.ViewModel.ChampionDetailViewModel
import com.example.championlike.Domain.UseCase.*
import com.example.championlike.Domain.ViewModel.ChampionViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl("http://ddragon.leagueoflegends.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(
        GsonBuilder()
            .registerTypeAdapter(List::class.java, Deserializers.ChampionNamesDeserializer())
            .registerTypeAdapter(Champion::class.java, Deserializers.ChampionDetailSerializer())
            .create())
    )
    .build()

private val championEndpoint: ChampionEndpoint = retrofit.create(ChampionEndpoint::class.java)

private val locale:String = "en_US"

val SHARED_PREFERENCES_NAME = "Default"

val appModule = module{
    single{
        Room.databaseBuilder(androidContext(),
            ChampionDatabase::class.java,"DB").build()
    }
    single{
        androidApplication().getSharedPreferences(SHARED_PREFERENCES_NAME,android.content.Context.MODE_PRIVATE)
    }
    single{
        FirebaseFirestore.getInstance()
    }
    single{
        ChampionFirebase(firebase = get())
    }
}

val networkModule = module{
    single { championEndpoint }
    single { ChampionAPI(championEndpoint=get(),locale=locale, pref = get()) }
}

val repositoryModule = module{
    single { ChampionRepositoryEndpoint(champAPI = get())}
    single { ChampionRepositoryDao(database = get())}
    single { ChampionRepositoryFirebase(firebase = get())}
}

val useCaseModule = module{
    factory { LoadChampionNamesUseCase(localRepository = get()) }
    factory { LoadChampionUseCase(repository =  get()) }
    factory { LoadChampionSkinUseCase(localRepository = get()) }
    factory { SaveChampionSplashUseCase(repository = get())}
    factory { SaveChampionNamesUseCase(localRepository =  get(),remoteRepository = get())}
    factory { SaveChampionSkinUseCase(localRepository =  get(),remoteRepository = get())}
    factory { UpdateChampionSkinUseCase(localRepository = get(),remoteRepository = get())}
    factory { LoadSkinPopularityUseCase(remoteRepository = get())}
}

val viewModelModule = module{
    viewModel{ ChampionViewModel(nameUseCase = get(), saveUseCase = get()) }
    viewModel{ ChampionDetailViewModel(loadChampion = get(), saveSkin = get(), loadSkin = get(), updateSkin = get(), getSkinPopularity = get()) }
}



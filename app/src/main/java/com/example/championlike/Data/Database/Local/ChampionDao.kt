package com.example.championlike.Data.Database.Local

import androidx.room.*
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.SkinEntity
import io.reactivex.Single

@Dao
interface ChampionDao{

    @Query("SELECT * FROM Champion")
    fun loadAllChampions(): Single<List<Champion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChampion(champ:Champion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChampions(vararg champs:Champion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChampions(champs:List<Champion>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkin(skin:SkinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkins(vararg skins:SkinEntity)

    @Query("SELECT * FROM SkinEntity WHERE champion_name=:name")
    fun getSkinsOfChampion(name:String): Single<List<SkinEntity>>

    @Query("SELECT * FROM SkinEntity WHERE champion_name=:name")
    fun getSkinsOfChampionBlocking(name:String): List<SkinEntity>

    @Query("UPDATE SkinEntity SET disliked=:disliked, liked=:liked WHERE champion_name=:name AND skin_id=:id")
    fun updateSkin(name:String, id:Int, liked:Boolean, disliked:Boolean):Single<Int>

    @Query("DELETE FROM SkinEntity")
    fun deleteSkinTable()

}
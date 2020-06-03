package com.example.championlike.Data.Database.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.SkinEntity

@Database(entities=[SkinEntity::class, Champion::class],version=1,exportSchema = false)
abstract class ChampionDatabase : RoomDatabase() {

    abstract fun championDaoAccess(): ChampionDao

}
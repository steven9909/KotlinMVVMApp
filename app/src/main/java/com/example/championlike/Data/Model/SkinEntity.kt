package com.example.championlike.Data.Model

import androidx.room.*
import com.example.championlike.Data.Model.Champion

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Champion::class,parentColumns = arrayOf("name"),childColumns = arrayOf("champion_name"))))
data class SkinEntity(@ColumnInfo(name="champion_name") var name:String, @ColumnInfo(name="skin_id") var skinId:Int, @ColumnInfo(name="liked") var liked:Boolean, @ColumnInfo(name="disliked") var disliked:Boolean){

    @Ignore
    lateinit var skinName:String

    @Ignore
    var likeNumber:Int = 0

    @Ignore
    var dislikeNumber:Int = 0

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    @Ignore
    constructor(name:String,skinId:Int,liked:Boolean,disliked:Boolean,skinName:String):this(name,skinId,liked,disliked){
        this.skinName = skinName
    }
}
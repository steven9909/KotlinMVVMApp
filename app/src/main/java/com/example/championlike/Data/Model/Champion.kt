package com.example.championlike.Data.Model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Champion(@PrimaryKey @ColumnInfo(name="name") var name:String){

    @Ignore
    var skins:HashMap<Int,String>? = null

    @Ignore
    lateinit var imageUrl:String

    @Ignore
    var liked:HashMap<Int,Boolean> = HashMap()

    @Ignore
    var disliked:HashMap<Int,Boolean> = HashMap()

    init{
        if(!this::imageUrl.isInitialized){
            imageUrl = SPLASH_BASE_URL+name+"_0.jpg"
        }
    }

    constructor(name:String, imageUrl:String):this(name){
        this.imageUrl = imageUrl
    }

    constructor(name:String,skins:HashMap<Int,String>):this(name){
        this.skins = skins
    }

    companion object{

        val SPLASH_BASE_URL:String = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Champion> = object:DiffUtil.ItemCallback<Champion>(){
            override fun areContentsTheSame(p0: Champion, p1: Champion): Boolean {
                return p0.name.equals(p1.name)
            }

            override fun areItemsTheSame(p0: Champion, p1: Champion): Boolean {
                return p0.equals(p1)
            }
        }

        fun getSkinUrl(id:Int,name:String):String{
            return SPLASH_BASE_URL+name+"_$id.jpg"
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other is Champion){
            return this.name.equals(other.name)
        }
        else{
            return false
        }
    }
}
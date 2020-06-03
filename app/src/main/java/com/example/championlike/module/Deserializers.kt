package com.example.championlike.module

import android.util.Log
import com.example.championlike.Data.Model.Champion
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class Deserializers {

    class ChampionNamesDeserializer: JsonDeserializer<List<Champion>> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): List<Champion> {

            val list:List<Champion> = ArrayList<Champion>()

            json?.let{
                val body = (it.asJsonObject.get("data") as JsonObject).keySet()

                body.forEach {
                    (list as ArrayList<Champion>).add(Champion(it.toString()))
                }

            }

            return list

        }
    }

    class ChampionDetailSerializer:JsonDeserializer<Champion>{
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Champion {

            json?.let {
                val body = it.asJsonObject.get("data")

                val name:String = body.asJsonObject.keySet().toString().drop(1).dropLast(1)

                val data = body.asJsonObject.get(name)

                val skins = data.asJsonObject.get("skins").asJsonArray

                val skinMap:HashMap<Int,String> = HashMap<Int,String>()

                for(item in skins){
                    Log.d("","")
                    if(!item.asJsonObject.get("name").asString.equals("default")){
                        item.asJsonObject.let{skin->
                            skinMap.put(skin.get("num").asInt,skin.get("name").asString)
                        }
                    }
                }

                return Champion(name,skinMap)
            }

            return Champion("")

        }
    }

}
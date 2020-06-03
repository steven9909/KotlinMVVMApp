package com.example.championlike.Data.Database.Remote

import com.example.championlike.Data.Model.Champion
import com.example.championlike.Data.Model.TaskResult
import com.example.championlike.Data.Model.SkinEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import io.reactivex.Observable

class ChampionFirebase(private val firebase:FirebaseFirestore) {

    private val championReference:CollectionReference = firebase.collection(baseCollectionURL)

    companion object{

        private val baseCollectionURL = "Champion"
        private val skinCollectionURL = "Skin"

        val IMAGE_URL_FILED_NAME:String = "imageUrl"

        val LIKED_FIELD_NAME:String = "liked"
        val DISLIKED_FIELD_NAME:String = "disliked"

    }

    fun getAllSkinsPopularityNumber(skins:List<SkinEntity>):Observable<Boolean>{
        val observables = ArrayList<Observable<Boolean>>()

        for(skin in skins){

            observables.add(getSkinPopularityNumber(skin))

        }

        return Observable.merge(observables)
    }

    private fun getSkinPopularityNumber(skin:SkinEntity): Observable<Boolean> {
        return Observable.create{ emitter->

            championReference
                .document(skin.name)
                .collection(skinCollectionURL)
                .document(skin.skinName)
                .get()
                .addOnCompleteListener { task->

                    task.result?.let{
                        skin.likeNumber = it[LIKED_FIELD_NAME].toString().toInt()
                        skin.dislikeNumber = it[DISLIKED_FIELD_NAME].toString().toInt()
                        emitter.onNext(task.isSuccessful)
                    }

                    emitter.onComplete()
                }.addOnFailureListener { e->
                    emitter.onError(e)
                }

            }
    }

    fun saveChampionSkin(entity: SkinEntity){
        getSkinDocument(entity)
            .set(hashMapOf(
                LIKED_FIELD_NAME to 0,
                DISLIKED_FIELD_NAME to 0
            ))
    }

    fun getChampionNames():Observable<List<Champion>>{
        throw NotImplementedError("Not implemented yet")
    }

    /**
     * Save champion names in firebase. No checking is done on whether the names already exist
     */
    fun saveChampionNames(champions:List<Champion>){
        for(champ in champions){
            val name:String = champ.name
            championReference.document(name).set(hashMapOf(
                IMAGE_URL_FILED_NAME to champ.imageUrl
            ))
        }
    }


    fun updateSkinPopularity(skinEntity: SkinEntity, incrementLiked:Boolean, incrementDisliked:Boolean, decrementLiked:Boolean, decrementDisliked:Boolean):Observable<TaskResult<SkinEntity>>{

        val observables:ArrayList<Observable<TaskResult<SkinEntity>>> = ArrayList()

        if(incrementLiked){
            observables.add(Observable.create{emitter->
                getSkinDocument(skinEntity).update(LIKED_FIELD_NAME, FieldValue.increment(1)).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        skinEntity.liked = true
                        emitter.onNext(TaskResult(true,skinEntity))
                    }
                    else{
                        emitter.onNext(TaskResult(false,skinEntity))
                    }
                    emitter.onComplete()
                }.addOnFailureListener { e->
                    emitter.onError(e)
                }
            })
        }

        if(incrementDisliked){
            observables.add(Observable.create{emitter->
                getSkinDocument(skinEntity).update(DISLIKED_FIELD_NAME, FieldValue.increment(1)).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        skinEntity.disliked = true
                        emitter.onNext(TaskResult(true,skinEntity))
                    }
                    else{
                        emitter.onNext(TaskResult(false,skinEntity))
                    }
                    emitter.onComplete()
                }.addOnFailureListener { e->
                    emitter.onError(e)
                }
            })
        }

        if(decrementDisliked){
            observables.add(Observable.create{emitter->
                getSkinDocument(skinEntity).update(DISLIKED_FIELD_NAME, FieldValue.increment(-1)).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        skinEntity.disliked = false
                        emitter.onNext(TaskResult(true,skinEntity))
                    }
                    else{
                        emitter.onNext(TaskResult(false,skinEntity))
                    }
                    emitter.onComplete()
                }.addOnFailureListener { e->
                    emitter.onError(e)
                }
            })
        }

        if(decrementLiked){
            observables.add(Observable.create{emitter->
                getSkinDocument(skinEntity).update(LIKED_FIELD_NAME, FieldValue.increment(-1)).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        skinEntity.liked = false
                        emitter.onNext(TaskResult(true,skinEntity))
                    }
                    else{
                        emitter.onNext(TaskResult(false,skinEntity))
                    }
                    emitter.onComplete()
                }.addOnFailureListener { e->
                    emitter.onError(e)
                }
            })
        }

        return Observable.merge(observables)

    }

    fun getSkinDocument(entity:SkinEntity):DocumentReference{
        return championReference
            .document(entity.name)
            .collection(skinCollectionURL)
            .document(entity.skinName)
    }



}
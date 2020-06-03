package com.example.championlike


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.championlike.Application.Fragment.ChampionSelectionFragment
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.rootLayout,
                    ChampionSelectionFragment(), ChampionSelectionFragment.NAME
                )
                .commit()
        }

    }

    override fun onStart(){
        super.onStart()

        val user = auth.currentUser

        if(user == null) {

            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        val curUser = auth.currentUser
                        curUser?.let{

                        }

                    } else {
                        finish()
                    }

                }
                .addOnFailureListener { e ->
                    finish()
                }
        }
    }
}

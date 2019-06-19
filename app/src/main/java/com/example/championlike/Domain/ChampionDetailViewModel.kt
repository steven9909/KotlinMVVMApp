package com.example.championlike.Domain

import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class ChampionDetailViewModel () : ViewModel() {

    @Inject
    lateinit var champRepo:ChampionRepository
}
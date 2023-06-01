package com.example.android.unscramble.ui.game


import android.util.Log
import androidx.lifecycle.ViewModel

val TAG = "Game Fragment"

class GameViewModel : ViewModel() {
    private var currentWordcount = 0
    private var score = 0

    //backing property
    private var _currentScrambleWord = "test"
    val currentScrambleWord: String get() = _currentScrambleWord

    //initializer block
    init {
        Log.d(TAG, "GameViewModel initiated")
    }

    //called when view model destroyed
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed!!!")
    }
}
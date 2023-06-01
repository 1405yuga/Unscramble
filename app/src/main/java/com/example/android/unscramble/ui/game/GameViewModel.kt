package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var currentWordcount =0
    private var score =0

    //backing property
    private var _currentScrambleWord ="test"
    val currentSrambleWord : String get() = _currentScrambleWord
}
package com.example.android.unscramble.ui.game


import android.util.Log
import androidx.lifecycle.ViewModel

val TAG = "Game Fragment"

class GameViewModel : ViewModel() {
    private var currentWordcount = 0
    private var score = 0
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    //backing property
    private  var _currentScrambleWord ="test"
    val currentScrambleWord: String get() = _currentScrambleWord

    private fun getNextWord() {
        Log.d(TAG, "getNextWord called!!")


        do {
            currentWord = allWordsList.random()
        } while (!wordsList.contains(currentWord))
        val tempWord = currentWord.toCharArray()

        //shuffle the words until it is not same as current word
        while (!tempWord.toString().equals(currentWord)) {
            tempWord.shuffle()
        }

        //check if it already used word
        _currentScrambleWord = String(tempWord)
        ++currentWordcount
        wordsList.add(currentWord)

    }

    private fun nextWord(): Boolean {
        return if (currentWordcount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

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
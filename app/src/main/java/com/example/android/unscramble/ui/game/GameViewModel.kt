package com.example.android.unscramble.ui.game


import android.util.Log
import androidx.lifecycle.ViewModel

val TAG = "Game Fragment"

class GameViewModel : ViewModel() {
    private var currentWordcount = 0

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    //backing property
    private lateinit var _currentScrambleWord : String
    val currentScrambleWord: String get() = _currentScrambleWord
    private var _score = 0
    val score : Int
        get() = _score

    private fun getNextWord() {
        Log.d(TAG, "getNextWord called!!")
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        //shuffle the words until it is not same as current word
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        //check if it already used word
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambleWord = String(tempWord)
            ++currentWordcount
            wordsList.add(currentWord)
        }

    }

    fun nextWord(): Boolean {
        return if (currentWordcount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    //initializer block
    init {
        Log.d(TAG, "GameViewModel initiated")
        getNextWord()
    }

    //called when view model destroyed
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed!!!")
    }

    private fun increaseScore(){
        _score+= SCORE_INCREASE
    }

    fun isUserWordCorrect(playWord : String) : Boolean{
        if(playWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }


}
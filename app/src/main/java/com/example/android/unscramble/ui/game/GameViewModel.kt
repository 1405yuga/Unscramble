package com.example.android.unscramble.ui.game


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

val TAG = "Game Fragment"

class GameViewModel : ViewModel() {


    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    //backing property
    private  var _currentScrambleWord = MutableLiveData<String>()
    val currentScrambleWord: LiveData<String> get() = _currentScrambleWord
    private var _score = 0
    val score : Int
        get() = _score
    private var _currentWordcount = 0
    val currentWordcount : Int get() = _currentWordcount

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
            _currentScrambleWord.value = String(tempWord)
            ++_currentWordcount
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

    fun reinitializeData(){
        _score=0
        _currentWordcount=0
        wordsList.clear()
        getNextWord()
    }


}
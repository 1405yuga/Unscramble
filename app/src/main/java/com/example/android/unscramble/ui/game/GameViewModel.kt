package com.example.android.unscramble.ui.game


import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

val TAG = "Game Fragment"

class GameViewModel : ViewModel() {


    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    //backing property
    private  var _currentScrambleWord = MutableLiveData<String>()
    //used it talkback to read scrambled word letter by letter
    val currentScrambleWord: LiveData<Spannable> = Transformations.map(_currentScrambleWord) {
        if (it == null) {
            SpannableString("")
        } else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }
    private var _score = MutableLiveData(0)
    val score : LiveData<Int>
        get() = _score
    private var _currentWordcount = MutableLiveData(0)
    val currentWordcount : LiveData<Int> get() = _currentWordcount

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
            _currentWordcount.value=_currentWordcount.value?.inc()
            wordsList.add(currentWord)
        }

    }

    fun nextWord(): Boolean {
        return if (currentWordcount.value !!< MAX_NO_OF_WORDS) {
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
        _score.value=(_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playWord : String) : Boolean{
        if(playWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData(){
        _score.value=0
        _currentWordcount.value=0
        wordsList.clear()
        getNextWord()
    }


}
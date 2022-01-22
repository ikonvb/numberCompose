package com.konstantinbulygin.numbercomposer.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.konstantinbulygin.numbercomposer.R
import com.konstantinbulygin.numbercomposer.data.GameRepositoryImpl
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult
import com.konstantinbulygin.numbercomposer.domain.entity.GameSettings
import com.konstantinbulygin.numbercomposer.domain.entity.Level
import com.konstantinbulygin.numbercomposer.domain.entity.Question
import com.konstantinbulygin.numbercomposer.domain.usecases.GetGameSettingsUseCase
import com.konstantinbulygin.numbercomposer.domain.usecases.GetQuestionUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var settings: GameSettings
    private lateinit var level: Level
    private val repository = GameRepositoryImpl
    private val getQuestionUseCase = GetQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val context = application
    private var timer: CountDownTimer? = null
    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        getQuestion()
        updateProgress()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent

        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            settings.minCountRightAnswers
        )

        _enoughCountOfRightAnswers.value = countOfRightAnswers >= settings.minCountRightAnswers
        _enoughPercentOfRightAnswers.value =
            percent >= settings.minPercentRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers.toDouble() / countOfQuestions) * 100).toInt()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        getQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }


    private fun getQuestion() {
        _question.value = getQuestionUseCase(settings.maxSumValue)
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.settings = getGameSettingsUseCase(level)
        _minPercent.value = settings.minPercentRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            settings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(ms: Long) {
                _formattedTime.value = formatTime(ms)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(ms: Long): String {
        val seconds = ms / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            settings
        )
    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()

    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}
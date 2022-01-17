package com.konstantinbulygin.numbercomposer.data

import com.konstantinbulygin.numbercomposer.domain.entity.GameSettings
import com.konstantinbulygin.numbercomposer.domain.entity.Level
import com.konstantinbulygin.numbercomposer.domain.entity.Question
import com.konstantinbulygin.numbercomposer.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 3
    private const val MIN_ANS_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANS_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)

        val from = max(rightAnswer - countOptions, MIN_ANS_VALUE)
        val to = min(maxSumValue, rightAnswer + countOptions)

        while (options.size < countOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(10, 3, 50, 8)
            }
            Level.EASY -> {
                GameSettings(10, 10, 70, 60)
            }
            Level.NORMAL -> {
                GameSettings(20, 20, 80, 40)
            }
            Level.HARD -> {
                GameSettings(30, 30, 90, 40)
            }
        }
    }
}
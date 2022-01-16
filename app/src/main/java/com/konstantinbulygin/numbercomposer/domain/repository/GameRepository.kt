package com.konstantinbulygin.numbercomposer.domain.repository

import com.konstantinbulygin.numbercomposer.domain.entity.GameSettings
import com.konstantinbulygin.numbercomposer.domain.entity.Level
import com.konstantinbulygin.numbercomposer.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOptions: Int): Question
    fun getGameSettings(level: Level): GameSettings
}
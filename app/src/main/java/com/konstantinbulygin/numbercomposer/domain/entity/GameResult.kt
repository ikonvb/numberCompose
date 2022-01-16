package com.konstantinbulygin.numbercomposer.domain.entity

data class GameResult(
    val winner: Boolean,
    val countRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings
) {
}
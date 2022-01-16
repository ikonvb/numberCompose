package com.konstantinbulygin.numbercomposer.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountRightAnswers: Int,
    val minPercentRightAnswers: Int,
    val gameTimeInSeconds: Int,
) {
}
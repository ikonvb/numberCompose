package com.konstantinbulygin.numbercomposer.domain.usecases

import com.konstantinbulygin.numbercomposer.domain.entity.Question
import com.konstantinbulygin.numbercomposer.domain.repository.GameRepository

class GetQuestionUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}
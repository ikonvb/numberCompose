package com.konstantinbulygin.numbercomposer.domain.usecases

import com.konstantinbulygin.numbercomposer.domain.entity.GameSettings
import com.konstantinbulygin.numbercomposer.domain.entity.Level
import com.konstantinbulygin.numbercomposer.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}
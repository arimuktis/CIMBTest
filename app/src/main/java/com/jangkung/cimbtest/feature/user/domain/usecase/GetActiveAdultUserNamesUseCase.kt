package com.jangkung.cimbtest.feature.user.domain.usecase

import com.jangkung.cimbtest.feature.user.domain.model.User

class GetActiveAdultUserNamesUseCase {
    operator fun invoke(users: List<User>): List<String> {
        return users
            .filter { it.isActive && it.age >= 18 }
            .map { it.name }
            .sorted()
    }
}

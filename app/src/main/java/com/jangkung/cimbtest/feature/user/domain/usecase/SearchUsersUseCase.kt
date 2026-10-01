package com.jangkung.cimbtest.feature.user.domain.usecase

import com.jangkung.cimbtest.feature.user.domain.model.User

class SearchUsersUseCase {
    operator fun invoke(users: List<User>, keyword: String): List<User> {
        if (keyword.isBlank()) return users.sortedBy { it.name }
        return users
            .filter { it.name.contains(keyword, ignoreCase = true) }
            .sortedBy { it.name }
    }
}

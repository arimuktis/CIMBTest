package com.jangkung.cimbtest.feature.user.domain.usecase

import com.jangkung.cimbtest.feature.user.domain.model.User
import com.jangkung.cimbtest.feature.user.domain.repository.UserRepository

class GetUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return repository.getUsers()
    }
}

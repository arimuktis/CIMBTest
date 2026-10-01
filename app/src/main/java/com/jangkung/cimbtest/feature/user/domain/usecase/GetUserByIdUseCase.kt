package com.jangkung.cimbtest.feature.user.domain.usecase

import com.jangkung.cimbtest.feature.user.domain.model.User
import com.jangkung.cimbtest.feature.user.data.dto.UserResponse
import com.jangkung.cimbtest.feature.user.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int): UserResponse {
        return repository.getUserById(id)
    }
}

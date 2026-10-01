package com.jangkung.cimbtest.feature.user.data.repository

import com.jangkung.cimbtest.feature.user.data.api.UserApi
import com.jangkung.cimbtest.feature.user.data.dto.UserResponse
import com.jangkung.cimbtest.feature.user.domain.model.User
import com.jangkung.cimbtest.feature.user.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        val response = api.getUsers()
        return response.map { dto ->
            User(
                id = dto.id,
                name = dto.name,
                age = dto.age,
                isActive = dto.isActive
            )
        }
    }

    override suspend fun getUserById(id: Int): UserResponse {
        return api.getUser(id)
    }
}

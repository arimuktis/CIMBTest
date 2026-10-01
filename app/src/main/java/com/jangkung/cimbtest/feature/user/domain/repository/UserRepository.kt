package com.jangkung.cimbtest.feature.user.domain.repository

import com.jangkung.cimbtest.feature.user.domain.model.User

import com.jangkung.cimbtest.feature.user.data.dto.UserResponse

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): UserResponse
}

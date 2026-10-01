package com.jangkung.cimbtest.feature.user.data.api

import com.jangkung.cimbtest.feature.user.data.dto.UserItemResponse
import com.jangkung.cimbtest.feature.user.data.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): UserResponse

    @GET("users")
    suspend fun getUsers(): List<UserItemResponse>
}

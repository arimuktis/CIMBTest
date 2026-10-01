package com.jangkung.cimbtest.feature.user.presentation

import com.jangkung.cimbtest.feature.user.domain.model.User

sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
}

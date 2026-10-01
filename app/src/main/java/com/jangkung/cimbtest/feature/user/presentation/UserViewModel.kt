package com.jangkung.cimbtest.feature.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jangkung.cimbtest.feature.user.domain.model.User
import com.jangkung.cimbtest.feature.user.domain.usecase.GetActiveAdultUserNamesUseCase
import com.jangkung.cimbtest.feature.user.domain.usecase.GetUsersUseCase
import com.jangkung.cimbtest.feature.user.domain.usecase.SearchUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import com.jangkung.cimbtest.feature.user.domain.usecase.GetUserByIdUseCase
import com.jangkung.cimbtest.feature.user.data.dto.UserResponse

class UserViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val getActiveAdultUserNamesUseCase: GetActiveAdultUserNamesUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Real-time filtered users based on uiState (Success) and searchQuery
    val filteredUsers: StateFlow<List<User>> = combine(_uiState, _searchQuery) { state, query ->
        if (state is UserUiState.Success) {
            searchUsersUseCase(state.users, query)
        } else {
            emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Static derived list for Requirement 1
    val activeAdultNames: StateFlow<List<String>> = _uiState.map { state ->
        if (state is UserUiState.Success) {
            getActiveAdultUserNamesUseCase(state.users)
        } else {
            emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                // The network call happens here safely inside viewModelScope Coroutine
                val users = getUsersUseCase()
                _uiState.value = UserUiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private val _selectedUserDetail = MutableStateFlow<UserResponse?>(null)
    val selectedUserDetail: StateFlow<UserResponse?> = _selectedUserDetail.asStateFlow()

    fun fetchUserDetail(id: Int) {
        viewModelScope.launch {
            try {
                val detail = getUserByIdUseCase(id)
                _selectedUserDetail.value = detail
            } catch (e: Exception) {
                e.printStackTrace()
                // You could also show a toast or error state here
            }
        }
    }

    fun clearSelectedUser() {
        _selectedUserDetail.value = null
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}

// Manual DI Factory
class UserViewModelFactory(
    private val getUsersUseCase: GetUsersUseCase,
    private val getActiveAdultUserNamesUseCase: GetActiveAdultUserNamesUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(getUsersUseCase, getActiveAdultUserNamesUseCase, searchUsersUseCase, getUserByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.jangkung.cimbtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jangkung.cimbtest.core.network.RetrofitInstance
import com.jangkung.cimbtest.feature.user.data.api.UserApi
import com.jangkung.cimbtest.feature.user.data.repository.UserRepositoryImpl
import com.jangkung.cimbtest.feature.user.domain.usecase.GetActiveAdultUserNamesUseCase
import com.jangkung.cimbtest.feature.user.domain.usecase.GetUsersUseCase
import com.jangkung.cimbtest.feature.user.domain.usecase.SearchUsersUseCase
import com.jangkung.cimbtest.feature.user.presentation.UserViewModel
import com.jangkung.cimbtest.feature.user.presentation.UserViewModelFactory
import com.jangkung.cimbtest.feature.user.presentation.screen.UserScreen
import com.jangkung.cimbtest.ui.theme.CIMBTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ----------------------------------------------------
        // MANUAL DEPENDENCY INJECTION (Lightweight Setup)
        // ----------------------------------------------------
        
        // 1. Core/Data Layer
        val api = RetrofitInstance.retrofit.create(UserApi::class.java)
        val repository = UserRepositoryImpl(api)

        // 2. Domain Layer (UseCases)
        val getUsersUseCase = GetUsersUseCase(repository)
        val getActiveAdultUseCase = GetActiveAdultUserNamesUseCase()
        val searchUsersUseCase = SearchUsersUseCase()
        val getUserByIdUseCase = com.jangkung.cimbtest.feature.user.domain.usecase.GetUserByIdUseCase(repository)

        // 3. Presentation Factory
        val factory = UserViewModelFactory(
            getUsersUseCase,
            getActiveAdultUseCase,
            searchUsersUseCase,
            getUserByIdUseCase
        )

        setContent {
            CIMBTestTheme {
                val viewModel: UserViewModel = viewModel(factory = factory)
                UserScreen(viewModel = viewModel)
            }
        }
    }
}
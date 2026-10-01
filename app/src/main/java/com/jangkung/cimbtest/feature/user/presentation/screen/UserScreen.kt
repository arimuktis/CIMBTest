package com.jangkung.cimbtest.feature.user.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jangkung.cimbtest.feature.user.presentation.UserUiState
import com.jangkung.cimbtest.feature.user.presentation.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: UserViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredUsers by viewModel.filteredUsers.collectAsState()
    val activeAdultNames by viewModel.activeAdultNames.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Android Kotlin Technical Test") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Requirement 1: Active Adult Users
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Req 1: Active Adult Users", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (activeAdultNames.isNotEmpty()) {
                            activeAdultNames.joinToString(", ")
                        } else {
                            "Calculating..."
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Requirement 5: Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                label = { Text("Search users by name (Req 5)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("User List (Req 2 & 3)", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            
            // UI State Handling (Requirement 2)
            when (uiState) {
                is UserUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UserUiState.Error -> {
                    val message = (uiState as UserUiState.Error).message
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.fetchUsers() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                is UserUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredUsers) { user ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { viewModel.fetchUserDetail(user.id) }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Name: ${user.name}", style = MaterialTheme.typography.bodyLarge)
                                    Text("Age: ${user.age}")
                                    Text("Status: ${if (user.isActive) "Active" else "Inactive"}")
                                }
                            }
                        }
                        
                        if (filteredUsers.isEmpty()) {
                            item {
                                Text(
                                    "No users found matching '$searchQuery'",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    val selectedDetail by viewModel.selectedUserDetail.collectAsState()
    selectedDetail?.let { detail ->
        AlertDialog(
            onDismissRequest = { viewModel.clearSelectedUser() },
            title = { Text(text = "User Details (Req 3)") },
            text = {
                Column {
                    Text("ID: ${detail.id}")
                    Text("Name: ${detail.name}")
                    Text("Email: ${detail.email}")
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.clearSelectedUser() }) {
                    Text("Close")
                }
            }
        )
    }
}

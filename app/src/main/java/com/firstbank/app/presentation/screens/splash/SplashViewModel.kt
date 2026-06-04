// app/src/main/java/com/firstbank/app/presentation/screens/splash/SplashViewModel.kt
package com.firstbank.app.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    init {
        viewModelScope.launch {
            delay(2000) // Minimum splash display time
            _isLoading.value = false
        }
    }

    fun isUserLoggedIn(): Boolean {
        // TODO: Check DataStore/SharedPreferences for auth token
        // Return true if user is logged in, false otherwise
        return false // Default to false for now
    }

    fun onNavigateToHome() {
        _navigateToHome.value = true
    }

    fun onNavigationHandled() {
        _navigateToHome.value = false
    }
}
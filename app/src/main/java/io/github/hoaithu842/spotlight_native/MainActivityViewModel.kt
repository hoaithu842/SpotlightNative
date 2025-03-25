package io.github.hoaithu842.spotlight_native

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.repository.PreferencesRepository
import io.github.hoaithu842.spotlight_native.domain.repository.UserRepository
import io.github.hoaithu842.spotlight_native.manager.SpotlightAccountManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountManager: SpotlightAccountManager,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _loginLoading = MutableStateFlow(false)
    val loginLoading: StateFlow<Boolean> = _loginLoading

    val userProfile = preferencesRepository.getLoggedIn().map {
        if (!it) {
            null
        } else {
            val userApiResponse = userRepository.getSessionUser()
            userApiResponse
        }
    }.map {
        if (it == null) {
            null
        } else {
            when (it) {
                is ApiResponse.Error -> null
                is ApiResponse.Exception -> null
                is ApiResponse.Success -> it.data
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun login(context: Context) = viewModelScope.launch {
        _loginLoading.update { true }
        accountManager.login(context)
        _loginLoading.update { false }
    }

    fun logout(context: Context) = viewModelScope.launch {
        _loginLoading.update { true }
        accountManager.logout(context)
        _loginLoading.update { false }
    }
}

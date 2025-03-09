package io.github.hoaithu842.spotlight_native

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    var appJustLaunched by mutableStateOf(true)
    var userIsAuthenticated by mutableStateOf(false)
    private var _profile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    private val TAG = "MainViewModel"
    private lateinit var account: Auth0
    private lateinit var context: Context

    fun login() {
        WebAuthProvider
            .login(account)
            .withScheme(BuildConfig.auth0Scheme)
            .start(context, object : Callback<Credentials, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // The user either pressed the “Cancel” button
                    // on the Universal Login screen or something
                    // unusual happened.
                    Log.e(TAG, "Error occurred in login(): $error")
                }

                override fun onSuccess(result: Credentials) {
                    // The user successfully logged in.
                    val idToken = result.idToken

                    // TODO: REMOVE BEFORE GOING TO PRODUCTION!
                    Log.d(TAG, "ID token: $idToken")

                    userIsAuthenticated = true
                    appJustLaunched = false
                    _profile.update {
                        result.user
                    }
                }

            })
    }

    fun logout() {
        WebAuthProvider
            .logout(account)
            .withScheme(BuildConfig.auth0Scheme)
            .start(context, object : Callback<Void?, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // For some reason, logout failed.
                    Log.e(TAG, "Error occurred in logout(): $error")
                }

                override fun onSuccess(result: Void?) {
                    // The user successfully logged out.
                    userIsAuthenticated = false
                    _profile.update {
                        null
                    }
                }

            })
    }

    fun setContext(activityContext: Context) {
        context = activityContext
        account = Auth0.getInstance(
            clientId = BuildConfig.auth0ClientId,
            domain = BuildConfig.auth0Domain,
        )
    }
}
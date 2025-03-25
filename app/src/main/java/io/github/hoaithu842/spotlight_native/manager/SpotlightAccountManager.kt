package io.github.hoaithu842.spotlight_native.manager

import android.content.Context
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import io.github.hoaithu842.spotlight_native.BuildConfig
import io.github.hoaithu842.spotlight_native.domain.repository.PreferencesRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class SpotlightAccountManager @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) : AccountManager {
    override val account: Auth0 by lazy {
        Auth0.getInstance(
            clientId = BuildConfig.auth0ClientId,
            domain = BuildConfig.auth0Domain,
        )
    }

    override suspend fun webAuthProviderLogin(context: Context): Result<Credentials> {
        return suspendCoroutine { cont ->
            WebAuthProvider
                .login(account)
                .withScheme(BuildConfig.auth0Scheme)
                .withAudience(audience = BuildConfig.auth0Audience)
                .start(context, object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        // The user either pressed the “Cancel” button
                        // on the Universal Login screen or something
                        // unusual happened.
                        Log.e("Rachel", "Error occurred in login(): $error")
                        cont.resume(Result.failure(Throwable(error.message)))
                    }

                    override fun onSuccess(result: Credentials) {
                        // The user successfully logged in.
                        cont.resume(Result.success(result))
                    }
                })
        }
    }

    override suspend fun login(context: Context) {
        webAuthProviderLogin(context)
            .onSuccess {
                preferencesRepository.setLoggedIn(true)
                preferencesRepository.setAccessToken(it.accessToken)
                preferencesRepository.setExpiresAt(it.expiresAt.toString())
            }
            .onFailure {
                Log.d("Rachel", "Fail with exception: ${it.message}")
            }
    }

    override suspend fun webAuthProviderLogout(context: Context): Result<Unit> {
        return suspendCoroutine { cont ->
            WebAuthProvider
                .logout(account)
                .withScheme(BuildConfig.auth0Scheme)
                .start(context, object : Callback<Void?, AuthenticationException> {

                    override fun onFailure(error: AuthenticationException) {
                        // For some reason, logout failed.
                        Log.e("Rachel", "Error occurred in logout(): $error")
                        cont.resume(Result.failure(Exception(error.message)))
                    }

                    override fun onSuccess(result: Void?) {
                        // The user successfully logged out.
                        cont.resume(Result.success(Unit))
                    }
                })
        }
    }

    override suspend fun logout(context: Context) {
        webAuthProviderLogout(context)
            .onSuccess {
                preferencesRepository.setLoggedIn(false)
                preferencesRepository.setAccessToken("")
                preferencesRepository.setExpiresAt("")
            }
    }
}
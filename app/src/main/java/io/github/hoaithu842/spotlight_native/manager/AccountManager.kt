package io.github.hoaithu842.spotlight_native.manager

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.result.Credentials

interface AccountManager {
    val account: Auth0

    suspend fun webAuthProviderLogin(context: Context): Result<Credentials>

    suspend fun login(context: Context)

    suspend fun webAuthProviderLogout(context: Context): Result<Unit>

    suspend fun logout(context: Context)
}

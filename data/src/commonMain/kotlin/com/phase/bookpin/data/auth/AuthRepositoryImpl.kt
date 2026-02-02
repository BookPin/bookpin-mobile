package com.phase.bookpin.data.auth

import com.phase.bookpin.data.api.auth.AuthRemoteDataSource
import com.phase.bookpin.data.api.auth.SocialAuthTokenRequest
import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.datastore.DataStoreKey
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.model.SocialAuthToken

class AuthRepositoryImpl(
    private val dataSource: AuthRemoteDataSource,
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : AuthRepository {

    override suspend fun login(token: SocialAuthToken): Result<Unit> =
        dataSource.login(SocialAuthTokenRequest.from(token))
            .onSuccess { response ->
                preferenceDataStore.saveString(DataStoreKey.ACCESS_TOKEN, response.accessToken)
                preferenceDataStore.saveString(DataStoreKey.REFRESH_TOKEN, response.refreshToken)
            }
            .map { }
}

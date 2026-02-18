package com.phase.bookpin.data.auth

import com.phase.bookpin.data.api.auth.AuthRemoteDataSource
import com.phase.bookpin.data.api.auth.model.DeviceAuthTokenRequest
import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.datastore.DataStoreKey
import com.phase.bookpin.domain.auth.AuthRepository
import com.phase.bookpin.model.auth.DeviceAuthToken

class AuthRepositoryImpl(
    private val dataSource: AuthRemoteDataSource,
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : AuthRepository {
    override suspend fun login(token: DeviceAuthToken): Result<Unit> =
        dataSource.login(DeviceAuthTokenRequest.from(token))
            .mapCatching { response ->
                preferenceDataStore.saveString(DataStoreKey.ACCESS_TOKEN, response.accessToken)
                preferenceDataStore.saveString(DataStoreKey.REFRESH_TOKEN, response.refreshToken)
            }
}

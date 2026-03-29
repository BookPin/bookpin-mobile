package com.phase.bookpin.data.user

import com.phase.bookpin.data.api.datastore.BookPinPreferenceDataStore
import com.phase.bookpin.data.api.datastore.DataStoreKey
import com.phase.bookpin.data.api.user.UserRemoteDataSource
import com.phase.bookpin.domain.user.UserRepository
import com.phase.bookpin.model.user.User
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val preferenceDataStore: BookPinPreferenceDataStore,
) : UserRepository {
    override suspend fun getUser(): Result<User> {
        val cachedId = preferenceDataStore.getString(DataStoreKey.USER_ID).first()
        val cachedNickname = preferenceDataStore.getString(DataStoreKey.USER_NICKNAME).first()
        val cachedImage = preferenceDataStore.getString(DataStoreKey.USER_PROFILE_IMAGE_URL).first()

        if (cachedId != null && cachedNickname != null) {
            return Result.success(User(cachedId.toLong(), cachedNickname, cachedImage.orEmpty()))
        }

        return remoteDataSource.getUser().mapCatching { response ->
            val user = response.toUser()
            preferenceDataStore.saveString(DataStoreKey.USER_ID, user.id.toString())
            preferenceDataStore.saveString(DataStoreKey.USER_NICKNAME, user.nickname)
            preferenceDataStore.saveString(DataStoreKey.USER_PROFILE_IMAGE_URL, user.profileImageUrl)
            user
        }
    }
}

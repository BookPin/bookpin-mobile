package com.phase.bookpin.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data object SplashRoute : NavKey

@Serializable
data object HomeRoute : NavKey

@Serializable
data object SearchRoute : NavKey

@Serializable
data class BookDetailRoute(
    val bookId: Long,
) : NavKey

@Serializable
data object SettingsRoute : NavKey

@Serializable
data class AddBookRoute(
    val title: String = "",
    val author: String = "",
    val totalPage: Int = -1,
    val imageUrl: String = "",
    val isbn: String = "",
) : NavKey

val navSerializersModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(SplashRoute::class, SplashRoute.serializer())
        subclass(HomeRoute::class, HomeRoute.serializer())
        subclass(SearchRoute::class, SearchRoute.serializer())
        subclass(BookDetailRoute::class, BookDetailRoute.serializer())
        subclass(SettingsRoute::class, SettingsRoute.serializer())
        subclass(AddBookRoute::class, AddBookRoute.serializer())
    }
}

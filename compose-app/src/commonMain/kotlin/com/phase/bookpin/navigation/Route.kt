package com.phase.bookpin.navigation

import androidx.navigation3.runtime.NavKey
import com.phase.bookpin.model.bookmark.BookmarkType
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
data class BookmarkTypeSelectRoute(
    val bookId: Long,
) : NavKey

@Serializable
data class AddBookmarkRoute(
    val bookId: Long,
    val bookmarkType: BookmarkType,
) : NavKey

@Serializable
data class BookmarkDetailRoute(
    val bookId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val bookImageUrl: String,
    val bookmarkId: Long,
    val pageNumber: Int,
    val extractedText: String,
    val note: String,
    val imageUrl: String,
    val createdAt: String,
) : NavKey

@Serializable
data class BookPreviewRoute(
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
        subclass(BookmarkTypeSelectRoute::class, BookmarkTypeSelectRoute.serializer())
        subclass(AddBookmarkRoute::class, AddBookmarkRoute.serializer())
        subclass(SettingsRoute::class, SettingsRoute.serializer())
        subclass(BookmarkDetailRoute::class, BookmarkDetailRoute.serializer())
        subclass(BookPreviewRoute::class, BookPreviewRoute.serializer())
    }
}

package com.phase.bookpin.data.remote.client

import kotlinx.serialization.Serializable

@Serializable
data class RemoteException(
    val view: NetworkExceptionViewType = NetworkExceptionViewType.TOAST,
    val type: String = "",
    val title: String = "알 수 없는 오류",
    val status: Int = -1,
    val detail: String = "알 수 없는 오류가 발생하였습니다.",
    val instance: String = "",
) : Exception() {
    enum class NetworkExceptionViewType {
        TOAST
    }

    fun toUnresolvedAddressException(): RemoteException = RemoteException(
        view = NetworkExceptionViewType.TOAST,
        type = type,
        title = "No Internet",
        status = 1000,
        detail = "인터넷 연결을 확인해주세요.",
        instance = "internal",
    )

    fun toSerializationException(): RemoteException = RemoteException(
        view = NetworkExceptionViewType.TOAST,
        type = type,
        title = "Serialization Error",
        status = 2000,
        detail = detail,
        instance = "internal",
    )
}

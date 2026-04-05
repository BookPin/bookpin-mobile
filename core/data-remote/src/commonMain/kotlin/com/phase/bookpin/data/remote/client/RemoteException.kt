package com.phase.bookpin.data.remote.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteException(
    val code: String = "",
    val status: Int = -1,
    @SerialName("message")
    val errorMessage: String = "알 수 없는 오류가 발생하였습니다.",
    val timestamp: String = "",
) : Exception(errorMessage) {

    fun toUnresolvedAddressException(): RemoteException = RemoteException(
        code = "UNRESOLVED_ADDRESS",
        status = 1000,
        errorMessage = "인터넷 연결을 확인해주세요.",
    )

    fun toSerializationException(): RemoteException = RemoteException(
        code = "SERIALIZATION_ERROR",
        status = 2000,
        errorMessage = errorMessage,
    )
}

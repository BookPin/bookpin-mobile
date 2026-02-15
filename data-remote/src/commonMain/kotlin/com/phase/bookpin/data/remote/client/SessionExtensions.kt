package com.phase.bookpin.data.remote.client

fun Throwable.shouldLogoutOnRefreshFailure(): Boolean {
    if (this !is RemoteException) return false
    return status == 401 || status == 403
}

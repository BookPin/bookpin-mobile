package com.phase.bookpin.auth

import com.phase.bookpin.common.BaseViewModel

class AuthViewModel : BaseViewModel<AuthState, AuthSideEffect>() {
    override fun createInitialState(): AuthState = AuthState()
}
